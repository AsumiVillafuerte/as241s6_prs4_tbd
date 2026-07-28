package pe.edu.vallegrande.sigrc.medications.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medications.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.medications.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.IDeleteMedicationUseCase;
import pe.edu.vallegrande.sigrc.medications.domain.ports.out.IMedicationRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteMedicationUseCaseImpl implements IDeleteMedicationUseCase {

    private final IMedicationRepository repository;

    @Override
    public Mono<Medication> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Medicamento no encontrado con id: " + id)))
                .flatMap(existing -> {
                    if ("inactive".equals(existing.getStatus())) {
                        return Mono.error(new DomainException("El medicamento ya está inactivo"));
                    }
                    return repository.changeStatus(id, "inactive");
                });
    }

    @Override
    public Mono<Medication> restore(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Medicamento no encontrado con id: " + id)))
                .flatMap(existing -> {
                    if ("active".equals(existing.getStatus())) {
                        return Mono.error(new DomainException("El medicamento ya está activo"));
                    }
                    return repository.changeStatus(id, "active");
                });
    }
}
