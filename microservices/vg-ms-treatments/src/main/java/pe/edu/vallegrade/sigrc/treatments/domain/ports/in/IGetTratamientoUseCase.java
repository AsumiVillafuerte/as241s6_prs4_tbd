package pe.edu.vallegrade.sigrc.treatments.domain.ports.in;

import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGetTratamientoUseCase {
    Mono<Tratamiento> getById(String id);
    Flux<Tratamiento> getAll();
}
