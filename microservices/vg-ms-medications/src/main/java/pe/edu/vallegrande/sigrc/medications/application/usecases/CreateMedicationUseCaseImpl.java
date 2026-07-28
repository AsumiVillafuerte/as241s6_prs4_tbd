package pe.edu.vallegrande.sigrc.medications.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medications.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.ICreateMedicationUseCase;
import pe.edu.vallegrande.sigrc.medications.domain.ports.out.IMedicationRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateMedicationUseCaseImpl implements ICreateMedicationUseCase {

    private final IMedicationRepository repository;

    @Override
    public Mono<Medication> create(Medication medication) {
        // Permite el mismo código si tiene diferente número de lote (principio FEFO)
        return repository.findByCodeAndBatchNumber(medication.getCode(), medication.getBatchNumber())
                .flatMap(existing -> Mono.<Medication>error(
                        new DomainException("Ya existe un medicamento con el código: "
                                + medication.getCode() + " y lote: " + medication.getBatchNumber())))
                .switchIfEmpty(repository.save(medication));
    }
}
