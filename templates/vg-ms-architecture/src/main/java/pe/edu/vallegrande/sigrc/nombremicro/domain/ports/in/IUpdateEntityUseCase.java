package pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in;

import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import reactor.core.publisher.Mono;

public interface IUpdateEntityUseCase {
    Mono<Entity> execute(String id, Entity entity);
}


