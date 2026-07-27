package pe.edu.vallegrande.sigrc.specialties.application.service;

import pe.edu.vallegrande.sigrc.specialties.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Specialty;
import pe.edu.vallegrande.sigrc.specialties.domain.port.input.SpecialtyUseCase;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.SpecialtyRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Supplier;

public class SpecialtyApplicationService implements SpecialtyUseCase {

    private final SpecialtyRepositoryPort repositoryPort;

    public SpecialtyApplicationService(SpecialtyRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Flux<Specialty> getAllSpecialties() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<Specialty> getSpecialtiesPaged(int page, int size) {
        if (page < 0 || size <= 0) {
            return Flux.error((Supplier<Throwable>) () -> new IllegalArgumentException("Parámetros de paginación inválidos"));
        }
        return repositoryPort.findAllPaged(page, size);
    }

    @Override
    public Mono<Long> countSpecialties() {
        return repositoryPort.countAll();
    }

    @Override
    public Mono<Specialty> getSpecialtyById(String id) {
        if (id == null || id.isBlank()) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El ID proporcionado no es válido"));
        }
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error((Supplier<Throwable>) () -> new NotFoundException("Especialidad no encontrada con ID: " + id)));
    }

    @Override
    public Mono<Specialty> getSpecialtyByCode(String code) {
        return repositoryPort.findByCode(code)
                .switchIfEmpty(Mono.error((Supplier<Throwable>) () -> new NotFoundException("Especialidad no encontrada con código: " + code)));
    }

    @Override
    public Flux<Specialty> getSpecialtiesByStatus(CommonStatus status) {
        return repositoryPort.findByStatus(status);
    }

    @Override
    public Mono<Specialty> registerSpecialty(Specialty specialty) {
        if (specialty == null) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("La especialidad no puede ser nula"));
        }
        return repositoryPort.findByCode(specialty.getCode())
                .flatMap(exists -> Mono.<Specialty>error((Supplier<Throwable>) () -> new IllegalArgumentException("El código de especialidad ya está registrado")))
                .switchIfEmpty(Mono.defer(() -> {
                    // Evitamos race-conditions clonando o inicializando de manera segura en aislamiento mutacional
                    specialty.initializeForCreation();
                    return repositoryPort.save(specialty);
                }));
    }

    @Override
    public Mono<Specialty> modifySpecialty(String id, Specialty updatedData) {
        return this.getSpecialtyById(id)
                .flatMap(current -> {
                    current.updateInfo(updatedData.getName(), updatedData.getDescription(), updatedData.getColor());
                    return repositoryPort.save(current);
                });
    }

    @Override
    public Mono<Specialty> activateSpecialty(String id) {
        return this.getSpecialtyById(id)
                .flatMap(specialty -> {
                    specialty.enable();
                    return repositoryPort.save(specialty);
                });
    }

    @Override
    public Mono<Specialty> deactivateSpecialty(String id) {
        return this.getSpecialtyById(id)
                .flatMap(specialty -> {
                    specialty.disable();
                    return repositoryPort.save(specialty);
                });
    }
}
