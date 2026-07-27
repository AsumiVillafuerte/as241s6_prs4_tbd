package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity.TherapyPriceDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TherapyPriceMongoRepository extends ReactiveMongoRepository<TherapyPriceDocument, String> {
    Flux<TherapyPriceDocument> findBySpecialtyId(String specialtyId);
}
