package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity.TherapyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TherapyMongoRepository extends ReactiveMongoRepository<TherapyDocument, String> {
    Mono<TherapyDocument> findByTicket(String ticket);
}
