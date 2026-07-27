package pe.edu.vallegrande.sigrc.suppliers.infrastructure.persistence.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.sigrc.suppliers.infrastructure.persistence.entities.SupplierEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SupplierR2dbcRepository extends ReactiveCrudRepository<SupplierEntity, Long> {

    Mono<SupplierEntity> findByDocumentNumber(String documentNumber);

    Mono<SupplierEntity> findByEmail(String email);

    Flux<SupplierEntity> findByStatus(Boolean status);

    Flux<SupplierEntity> findByDocumentType(String documentType);
}
