package pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.entities.SaleItemEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SaleItemR2dbcRepository extends R2dbcRepository<SaleItemEntity, UUID> {
    Flux<SaleItemEntity> findBySaleId(UUID saleId);
    Mono<Void> deleteBySaleId(UUID saleId);
}
