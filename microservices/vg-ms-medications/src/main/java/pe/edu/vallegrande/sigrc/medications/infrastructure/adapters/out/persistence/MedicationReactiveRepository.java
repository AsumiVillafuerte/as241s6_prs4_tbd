package pe.edu.vallegrande.sigrc.medications.infrastructure.adapters.out.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicationReactiveRepository extends ReactiveMongoRepository<MedicationDocument, String> {
    Flux<MedicationDocument> findByStatus(String status);
    Mono<MedicationDocument> findByCode(String code);
    Mono<MedicationDocument> findByCodeAndBatchNumber(String code, String batchNumber);
}
