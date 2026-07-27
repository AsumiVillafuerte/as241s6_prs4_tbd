package pe.edu.vallegrande.sigrc.shopping.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;
import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;
import pe.edu.vallegrande.sigrc.shopping.domain.port.in.IRevokeCompraUseCase;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.ICompraRepository;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IDetalleCompraRepository;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IMedicamentoClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RevokeCompraUseCaseImpl implements IRevokeCompraUseCase {

    private final ICompraRepository compraRepository;
    private final IDetalleCompraRepository detalleRepository;
    private final IMedicamentoClient medicamentoClient;

    @Override
    @Transactional
    public Mono<Compra> execute(Long id) {
        return compraRepository.findById(id)
                .flatMap(compra -> {
                    compra.revocar();
                    return detalleRepository.findByCompraId(id)
                            .collectList()
                            .flatMap(detalles -> eliminarLotes(detalles)
                                    .then(compraRepository.update(compra))
                            );
                });
    }

    private Mono<Void> eliminarLotes(List<DetalleCompraMedicamento> detalles) {
        return Flux.fromIterable(detalles)
                .filter(d -> d.getLoteInventarioId() != null)
                .flatMap(d -> medicamentoClient.eliminarLote(d.getLoteInventarioId())
                        .onErrorResume(e -> Mono.empty())
                )
                .then();
    }
}
