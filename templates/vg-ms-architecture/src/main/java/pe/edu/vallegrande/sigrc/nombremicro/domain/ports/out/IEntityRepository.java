package pe.edu.vallegrande.sigrc.nombremicro.domain.ports.out;

import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEntityRepository {
    Mono<Entity> save(Entity entity);
    Mono<Entity> findById(String id);
    Flux<Entity> findAll();
    Flux<Entity> findByStatus(String status);
    Mono<Boolean> existsById(String id);
}


