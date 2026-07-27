package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IDetalleCompraRepository;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.entity.DetalleCompraMedicamentoEntity;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.repository.DetalleCompraR2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DetalleCompraRepositoryAdapter implements IDetalleCompraRepository {

    private final DetalleCompraR2dbcRepository r2dbcRepository;
    private final DatabaseClient databaseClient;

    @Override
    public Flux<DetalleCompraMedicamento> saveAll(List<DetalleCompraMedicamento> detalles) {
        List<DetalleCompraMedicamentoEntity> entities = detalles.stream()
                .map(this::toEntity)
                .toList();
        return r2dbcRepository.saveAll(entities).map(this::toDomain);
    }

    @Override
    public Flux<DetalleCompraMedicamento> findByCompraId(Long compraId) {
        return r2dbcRepository.findByCompraId(compraId).map(this::toDomain);
    }

    @Override
    public Mono<Void> updateLoteInventarioId(Long detalleId, String loteInventarioId) {
        return databaseClient.sql(
                        "UPDATE detalle_compra_medicamento SET lote_inventario_id = :loteId WHERE id = :id")
                .bind("loteId", loteInventarioId)
                .bind("id", detalleId)
                .fetch()
                .rowsUpdated()
                .then();
    }

    private DetalleCompraMedicamentoEntity toEntity(DetalleCompraMedicamento d) {
        return DetalleCompraMedicamentoEntity.builder()
                .id(d.getId())
                .compraId(d.getCompraId())
                .medicamentoId(d.getMedicamentoId())
                .denominacionComercial(d.getDenominacionComercial())
                .denominacionGenerica(d.getDenominacionGenerica())
                .laboratorio(d.getLaboratorio())
                .presentacion(d.getPresentacion())
                .lote(d.getLote())
                .vencimiento(d.getVencimiento())
                .ubicacion(d.getUbicacion())
                .precioCompra(d.getPrecioCompra())
                .precioVenta(d.getPrecioVenta())
                .cantidad(d.getCantidad())
                .minStock(d.getMinStock())
                .total(d.getTotal())
                .markupPorcentaje(d.getMarkupPorcentaje())
                .loteInventarioId(d.getLoteInventarioId())
                .createdAt(d.getCreatedAt() != null ? d.getCreatedAt() : LocalDateTime.now())
                .build();
    }

    private DetalleCompraMedicamento toDomain(DetalleCompraMedicamentoEntity e) {
        return DetalleCompraMedicamento.builder()
                .id(e.getId())
                .compraId(e.getCompraId())
                .medicamentoId(e.getMedicamentoId())
                .denominacionComercial(e.getDenominacionComercial())
                .denominacionGenerica(e.getDenominacionGenerica())
                .laboratorio(e.getLaboratorio())
                .presentacion(e.getPresentacion())
                .lote(e.getLote())
                .vencimiento(e.getVencimiento())
                .ubicacion(e.getUbicacion())
                .precioCompra(e.getPrecioCompra())
                .precioVenta(e.getPrecioVenta())
                .cantidad(e.getCantidad())
                .minStock(e.getMinStock())
                .total(e.getTotal())
                .markupPorcentaje(e.getMarkupPorcentaje())
                .loteInventarioId(e.getLoteInventarioId())
                .createdAt(e.getCreatedAt())
                .build();
    }
}
