package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity.TicketSequenceDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TicketSequenceMongoRepository extends ReactiveMongoRepository<TicketSequenceDocument, String> {
    Mono<TicketSequenceDocument> findByDate(String date);
}
