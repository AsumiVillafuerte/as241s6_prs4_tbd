package pe.edu.vallegrande.sigrc.nombremicro.infrastructure.adapters.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EntityR2dbcRepository extends ReactiveCrudRepository<EntityDocument, Long> {
    Flux<EntityDocument> findByStatus(String status);
}
