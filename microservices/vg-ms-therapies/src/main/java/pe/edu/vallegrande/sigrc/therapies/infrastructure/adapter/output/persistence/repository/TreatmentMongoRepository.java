package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity.TreatmentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TreatmentMongoRepository extends ReactiveMongoRepository<TreatmentDocument, String> {
    Flux<TreatmentDocument> findBySpecialtyId(String specialtyId);
}
