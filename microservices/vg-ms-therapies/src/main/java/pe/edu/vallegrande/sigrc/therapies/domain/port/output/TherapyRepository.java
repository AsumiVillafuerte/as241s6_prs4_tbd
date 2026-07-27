package pe.edu.vallegrande.sigrc.therapies.domain.port.output;

import pe.edu.vallegrande.sigrc.therapies.domain.model.Therapy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TherapyRepository {
    Flux<Therapy> findAll();
    Flux<Therapy> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<Therapy> findById(String id);
    Mono<Therapy> findByTicket(String ticket);
    Mono<Therapy> save(Therapy therapy);
    Mono<Void> deleteById(String id);
}
