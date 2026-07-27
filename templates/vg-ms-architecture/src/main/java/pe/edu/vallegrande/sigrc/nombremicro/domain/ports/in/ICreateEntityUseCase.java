package pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in;

import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import reactor.core.publisher.Mono;

public interface ICreateEntityUseCase {
    Mono<Entity> execute(Entity entity);
}


