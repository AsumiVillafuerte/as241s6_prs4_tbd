package pe.edu.vallegrande.sigrc.shopping.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.BusinessRuleException;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.DomainException;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.DuplicateComprobanteException;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.ExternalServiceException;
import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;
import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;
import pe.edu.vallegrande.sigrc.shopping.domain.model.EstadoCompra;
import pe.edu.vallegrande.sigrc.shopping.domain.model.TipoCompra;
import pe.edu.vallegrande.sigrc.shopping.domain.port.in.ICreateCompraUseCase;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.ICompraRepository;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IDetalleCompraRepository;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IMedicamentoClient;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IProveedorClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCompraUseCaseImpl implements ICreateCompraUseCase {

    private final ICompraRepository compraRepository;
    private final IDetalleCompraRepository detalleRepository;
    private final IProveedorClient proveedorClient;
    private final IMedicamentoClient medicamentoClient;

    @Override
    @Transactional
    public Mono<Compra> execute(Compra compra) {
        return compraRepository.existsByNumeroComprobante(compra.getNumeroComprobante())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new DuplicateComprobanteException(compra.getNumeroComprobante())))
                .then(proveedorClient.findById(compra.getProveedorId()))
                .flatMap(proveedor -> {
                    if (Boolean.FALSE.equals(proveedor.status()))
                        return Mono.error(new BusinessRuleException("El proveedor se encuentra inactivo"));
                    return enriquecerDetalles(compra.getDetalles());
                })
                .flatMap(detallesEnriquecidos -> {
                    compra.setDetalles(detallesEnriquecidos);
                    compra.calcularTotales();
                    compra.setEstado(EstadoCompra.CONSIGNADO);
                    compra.setTipo(TipoCompra.COMPRADO);
                    compra.setFechaCompra(LocalDateTime.now());
                    // Primero registrar todos los lotes en ms-medications.
                    // Si falla aquí, nuestra BD no recibe ningún INSERT y la secuencia no avanza.
                    return crearLotesEnMedicamentos(detallesEnriquecidos);
                })
                .then(compraRepository.save(compra))
                .flatMap(compraSaved -> {
                    List<DetalleCompraMedicamento> detalles = compra.getDetalles().stream()
                            .peek(d -> d.setCompraId(compraSaved.getId()))
                            .toList();
                    return detalleRepository.saveAll(detalles)
                            .collectList()
                            .map(savedList -> {
                                for (int i = 0; i < savedList.size(); i++) {
                                    detalles.get(i).setId(savedList.get(i).getId());
                                }
                                compraSaved.setDetalles(detalles);
                                return compraSaved;
                            });
                });
    }

    private Mono<List<DetalleCompraMedicamento>> enriquecerDetalles(List<DetalleCompraMedicamento> detalles) {
        return Flux.fromIterable(detalles)
                .flatMap(d -> medicamentoClient.findById(d.getMedicamentoId())
                        .map(med -> {
                            d.setDenominacionComercial(med.denominacionComercial());
                            d.setDenominacionGenerica(med.denominacionGenerica());
                            d.setMedicamentoInfo(med);
                            d.calcularTotal();
                            return d;
                        })
                )
                .collectList();
    }

    private Mono<List<DetalleCompraMedicamento>> crearLotesEnMedicamentos(List<DetalleCompraMedicamento> detalles) {
        return Flux.fromIterable(detalles)
                .flatMap(detalle -> medicamentoClient.crearLote(detalle.getMedicamentoInfo(), detalle)
                        .map(loteId -> {
                            detalle.setLoteInventarioId(loteId);
                            return detalle;
                        })
                        .onErrorMap(e -> !(e instanceof DomainException),
                                e -> new ExternalServiceException("ms-medications", e))
                )
                .collectList();
    }
}
