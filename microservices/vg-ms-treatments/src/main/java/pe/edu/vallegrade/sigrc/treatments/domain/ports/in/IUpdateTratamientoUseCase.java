package pe.edu.vallegrade.sigrc.treatments.domain.ports.in;

import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import reactor.core.publisher.Mono;

public interface IUpdateTratamientoUseCase {
    Mono<Tratamiento> execute(String id, Tratamiento tratamiento);
}
