package pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in;

import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGetEntityUseCase {
    Mono<Entity> findById(String id);
    Flux<Entity> findAll();
    Flux<Entity> findByStatus(String status);
}


