package pe.edu.vallegrande.sigrc.shopping.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;
import pe.edu.vallegrande.sigrc.shopping.domain.port.in.IGetCompraUseCase;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.ICompraRepository;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IDetalleCompraRepository;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IProveedorClient;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IUsuarioClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetCompraUseCaseImpl implements IGetCompraUseCase {

    private final ICompraRepository compraRepository;
    private final IDetalleCompraRepository detalleRepository;
    private final IUsuarioClient usuarioClient;
    private final IProveedorClient proveedorClient;

    @Override
    public Mono<Compra> execute(Long id) {
        return compraRepository.findById(id)
                .flatMap(compra -> detalleRepository.findByCompraId(id)
                        .collectList()
                        .doOnNext(compra::setDetalles)
                        .thenReturn(compra)
                )
                .flatMap(compra -> Mono.zip(
                                usuarioClient.findById(compra.getUsuarioId()),
                                proveedorClient.findById(compra.getProveedorId())
                        )
                        .map(tuple -> {
                            compra.setUsuarioInfo(tuple.getT1());
                            compra.setProveedorInfo(tuple.getT2());
                            return compra;
                        })
                );
    }
}
