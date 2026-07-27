package pe.edu.vallegrande.sigrc.shopping.domain.port.out;

import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;
import pe.edu.vallegrande.sigrc.shopping.domain.model.MedicamentoInfo;
import reactor.core.publisher.Mono;

public interface IMedicamentoClient {

    Mono<MedicamentoInfo> findById(String medicamentoId);

    Mono<String> crearLote(MedicamentoInfo medicamentoInfo, DetalleCompraMedicamento detalle);

    Mono<Void> eliminarLote(String loteInventarioId);
}
