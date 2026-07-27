package pe.edu.vallegrande.sigrc.specialties.application.service;

import pe.edu.vallegrande.sigrc.specialties.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.specialties.domain.port.input.TreatmentUseCase;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.TreatmentRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Supplier;

public class TreatmentApplicationService implements TreatmentUseCase {

    private final TreatmentRepositoryPort repositoryPort;

    public TreatmentApplicationService(TreatmentRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Flux<Treatment> getAllTreatments() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<Treatment> getTreatmentsPaged(int page, int size) {
        if (page < 0 || size <= 0) {
            return Flux.error((Supplier<Throwable>) () -> new IllegalArgumentException("Parámetros de paginación inválidos"));
        }
        return repositoryPort.findAllPaged(page, size);
    }

    @Override
    public Mono<Long> countTreatments() {
        return repositoryPort.countAll();
    }

    @Override
    public Mono<Treatment> getTreatmentById(String id) {
        if (id == null || id.isBlank()) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El ID proporcionado no es válido"));
        }
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error((Supplier<Throwable>) () -> new NotFoundException("Tratamiento no encontrado con ID: " + id)));
    }

    @Override
    public Mono<Treatment> getTreatmentByCode(String code) {
        return repositoryPort.findByCode(code)
                .switchIfEmpty(Mono.error((Supplier<Throwable>) () -> new NotFoundException("Tratamiento no encontrado con código: " + code)));
    }

    @Override
    public Flux<Treatment> getTreatmentsBySpecialty(String specialtyId) {
        return repositoryPort.findBySpecialtyId(specialtyId);
    }

    @Override
    public Mono<Treatment> registerTreatment(Treatment treatment) {
        if (treatment == null) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El tratamiento no puede ser nulo"));
        }
        return repositoryPort.findByCode(treatment.getCode())
                .flatMap(exists -> Mono.<Treatment>error((Supplier<Throwable>) () -> new IllegalArgumentException("El código de tratamiento ya está registrado")))
                .switchIfEmpty(Mono.defer(() -> {
                    treatment.initializeForCreation();
                    return repositoryPort.save(treatment);
                }));
    }

    @Override
    public Mono<Treatment> modifyTreatment(String id, Treatment updatedData) {
        return this.getTreatmentById(id)
                .flatMap(current -> {
                    current.updateInfo(updatedData.getName(), updatedData.getSpecialtyId());
                    return repositoryPort.save(current);
                });
    }

    @Override
    public Mono<Treatment> activateTreatment(String id) {
        return this.getTreatmentById(id)
                .flatMap(treatment -> {
                    treatment.enable();
                    return repositoryPort.save(treatment);
                });
    }

    @Override
    public Mono<Treatment> deactivateTreatment(String id) {
        return this.getTreatmentById(id)
                .flatMap(treatment -> {
                    treatment.disable();
                    return repositoryPort.save(treatment);
                });
    }
}
