package pe.edu.vallegrande.sigrc.therapies.domain.port.output;

import pe.edu.vallegrande.sigrc.therapies.domain.model.TherapyPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TherapyPriceRepository {
    Flux<TherapyPrice> findAll();
    Flux<TherapyPrice> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<TherapyPrice> findById(String id);
    Flux<TherapyPrice> findBySpecialtyId(String specialtyId);
    Mono<TherapyPrice> save(TherapyPrice therapyPrice);
    Mono<Void> deleteById(String id);
}
