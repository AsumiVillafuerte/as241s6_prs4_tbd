package pe.edu.vallegrande.sigrc.nombremicro.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.nombremicro.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in.IGetEntityUseCase;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.out.IEntityRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEntityUseCaseImpl implements IGetEntityUseCase {

    private final IEntityRepository entityRepository;

    @Override
    public Mono<Entity> findById(String id) {
        log.info("Finding entity by id: {}", id);
        return entityRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Entity not found with id: " + id)));
    }

    @Override
    public Flux<Entity> findAll() {
        log.info("Finding all entities");
        return entityRepository.findAll();
    }

    @Override
    public Flux<Entity> findByStatus(String status) {
        log.info("Finding entities by status: {}", status);
        return entityRepository.findByStatus(status);
    }
}


