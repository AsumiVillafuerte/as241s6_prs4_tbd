package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.entity.ClientTypeDocument;
import reactor.core.publisher.Flux;

public interface ClientTypeMongoRepository extends ReactiveMongoRepository<ClientTypeDocument, String> {
    Flux<ClientTypeDocument> findByStatus(String status);
    Flux<ClientTypeDocument> findAllBy(Pageable pageable);
}