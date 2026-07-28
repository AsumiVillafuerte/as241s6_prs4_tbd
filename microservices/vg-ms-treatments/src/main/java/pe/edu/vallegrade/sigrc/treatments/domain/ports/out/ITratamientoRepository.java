package pe.edu.vallegrade.sigrc.treatments.domain.ports.out;

import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITratamientoRepository {
    Mono<Tratamiento> save(Tratamiento tratamiento);
    Mono<Tratamiento> findById(String id);
    Flux<Tratamiento> findAll();
    Mono<Tratamiento> update(String id, Tratamiento tratamiento);
    Mono<Tratamiento> changeEstado(String id, String nuevoEstado);
    Mono<Tratamiento> changeTipo(String id, String nuevoTipo);
    Mono<Long> countAll();
}
