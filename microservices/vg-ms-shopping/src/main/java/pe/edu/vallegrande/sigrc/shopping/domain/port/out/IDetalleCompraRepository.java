package pe.edu.vallegrande.sigrc.shopping.domain.port.out;

import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IDetalleCompraRepository {

    Flux<DetalleCompraMedicamento> saveAll(List<DetalleCompraMedicamento> detalles);

    Flux<DetalleCompraMedicamento> findByCompraId(Long compraId);

    Mono<Void> updateLoteInventarioId(Long detalleId, String loteInventarioId);
}
