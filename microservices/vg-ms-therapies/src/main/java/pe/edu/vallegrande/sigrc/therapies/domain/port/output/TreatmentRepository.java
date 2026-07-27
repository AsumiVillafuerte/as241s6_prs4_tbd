package pe.edu.vallegrande.sigrc.therapies.domain.port.output;

import pe.edu.vallegrande.sigrc.therapies.domain.model.Treatment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreatmentRepository {
    Flux<Treatment> findAll();
    Flux<Treatment> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<Treatment> findById(String id);
    Flux<Treatment> findBySpecialtyId(String specialtyId);
    Mono<Treatment> save(Treatment treatment);
    Mono<Void> deleteById(String id);
}
