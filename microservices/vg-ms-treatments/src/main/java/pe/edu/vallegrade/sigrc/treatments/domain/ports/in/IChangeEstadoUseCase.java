package pe.edu.vallegrade.sigrc.treatments.domain.ports.in;

import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import reactor.core.publisher.Mono;

public interface IChangeEstadoUseCase {
    Mono<Tratamiento> execute(String id, String nuevoEstado);
}
