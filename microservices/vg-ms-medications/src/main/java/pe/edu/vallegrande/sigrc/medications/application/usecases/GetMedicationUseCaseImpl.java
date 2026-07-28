package pe.edu.vallegrande.sigrc.medications.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medications.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.IGetMedicationUseCase;
import pe.edu.vallegrande.sigrc.medications.domain.ports.out.IMedicationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetMedicationUseCaseImpl implements IGetMedicationUseCase {

    private final IMedicationRepository repository;

    @Override
    public Flux<Medication> getAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Medication> getAllInactive() {
        return repository.findAllInactive();
    }

    @Override
    public Mono<Medication> getById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Medicamento no encontrado con id: " + id)));
    }
}
