package pe.edu.vallegrande.sigrc.medications.domain.ports.in;

import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import reactor.core.publisher.Mono;

public interface ICreateMedicationUseCase {
    Mono<Medication> create(Medication medication);
}
