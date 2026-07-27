package pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in;

import reactor.core.publisher.Mono;

public interface IDeleteEntityUseCase {
    Mono<Void> execute(String id);
}


