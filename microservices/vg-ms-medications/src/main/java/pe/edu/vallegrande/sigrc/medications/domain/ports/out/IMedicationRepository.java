package pe.edu.vallegrande.sigrc.medications.domain.ports.out;

import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMedicationRepository {
    Flux<Medication> findAll();
    Flux<Medication> findAllInactive();
    Mono<Medication> findById(String id);
    Mono<Medication> findByCode(String code);
    Mono<Medication> findByCodeAndBatchNumber(String code, String batchNumber);
    Mono<Medication> save(Medication medication);
    Mono<Medication> update(String id, Medication medication);
    Mono<Medication> changeStatus(String id, String status);
}
