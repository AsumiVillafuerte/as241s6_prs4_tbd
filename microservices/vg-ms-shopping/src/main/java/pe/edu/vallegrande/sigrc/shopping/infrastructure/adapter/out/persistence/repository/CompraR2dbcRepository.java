package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.entity.CompraEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompraR2dbcRepository extends ReactiveCrudRepository<CompraEntity, Long> {

    Mono<Boolean> existsByNumeroComprobante(String numeroComprobante);

    @Query("SELECT * FROM compras ORDER BY fecha_compra DESC")
    Flux<CompraEntity> findAllOrderByFechaCompraDesc();
}
