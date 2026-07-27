package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.entity.TreatmentDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreatmentMongoRepository extends ReactiveMongoRepository<TreatmentDocument, String> {
    Flux<TreatmentDocument> findBySpecialtyId(String specialtyId);
    Mono<TreatmentDocument> findByCode(String code);
    Flux<TreatmentDocument> findAllBy(Pageable pageable);
    Flux<TreatmentDocument> findByStatus(String status);
}