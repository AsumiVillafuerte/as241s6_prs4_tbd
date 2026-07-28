package pe.edu.vallegrande.sigrc.medications.domain.ports.in;

import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGetMedicationUseCase {
    Flux<Medication> getAll();
    Flux<Medication> getAllInactive();
    Mono<Medication> getById(String id);
}
