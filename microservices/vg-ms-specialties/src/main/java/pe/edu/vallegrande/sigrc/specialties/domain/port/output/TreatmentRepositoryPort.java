package pe.edu.vallegrande.sigrc.specialties.domain.port.output;

import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Treatment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreatmentRepositoryPort {
    Flux<Treatment> findAll();
    Flux<Treatment> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<Treatment> findById(String id);
    Mono<Treatment> findByCode(String code);
    Flux<Treatment> findBySpecialtyId(String specialtyId);
    Flux<Treatment> findByStatus(CommonStatus status);
    Mono<Treatment> save(Treatment treatment);
}
