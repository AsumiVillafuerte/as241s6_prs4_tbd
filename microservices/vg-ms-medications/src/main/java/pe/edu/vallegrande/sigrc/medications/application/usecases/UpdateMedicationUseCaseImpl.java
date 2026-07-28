package pe.edu.vallegrande.sigrc.medications.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medications.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.IUpdateMedicationUseCase;
import pe.edu.vallegrande.sigrc.medications.domain.ports.out.IMedicationRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateMedicationUseCaseImpl implements IUpdateMedicationUseCase {

    private final IMedicationRepository repository;

    @Override
    public Mono<Medication> update(String id, Medication medication) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Medicamento no encontrado con id: " + id)))
                .flatMap(existing -> repository.update(id, medication));
    }
}
