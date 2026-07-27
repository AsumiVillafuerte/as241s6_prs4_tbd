package pe.edu.vallegrande.sigrc.product.infrastructure.persistence.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.entities.BrandEntity;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface BrandR2dbcRepository extends R2dbcRepository<BrandEntity, UUID> {
    Flux<BrandEntity> findByStatus(String status);
}
