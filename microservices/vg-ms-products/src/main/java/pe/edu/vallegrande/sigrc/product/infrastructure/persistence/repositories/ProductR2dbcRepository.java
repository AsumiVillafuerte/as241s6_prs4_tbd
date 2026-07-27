package pe.edu.vallegrande.sigrc.product.infrastructure.persistence.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.entities.ProductEntity;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductR2dbcRepository extends R2dbcRepository<ProductEntity, UUID> {
    Flux<ProductEntity> findByStatus(String status);
}
