package pe.edu.vallegrande.sigrc.medications.domain.ports.in;

import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import reactor.core.publisher.Mono;

public interface IDeleteMedicationUseCase {
    Mono<Medication> delete(String id);
    Mono<Medication> restore(String id);
}
