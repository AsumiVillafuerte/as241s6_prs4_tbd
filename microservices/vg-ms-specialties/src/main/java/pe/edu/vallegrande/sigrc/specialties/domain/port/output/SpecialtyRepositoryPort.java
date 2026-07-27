package pe.edu.vallegrande.sigrc.specialties.domain.port.output;

import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Specialty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpecialtyRepositoryPort {
    Flux<Specialty> findAll();
    Flux<Specialty> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<Specialty> findById(String id);
    Mono<Specialty> findByCode(String code);
    Flux<Specialty> findByStatus(CommonStatus status);
    Mono<Specialty> save(Specialty specialty);
}
