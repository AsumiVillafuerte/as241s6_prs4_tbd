package pe.edu.vallegrande.sigrc.nombremicro.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.nombremicro.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in.IUpdateEntityUseCase;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.out.IEntityRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEntityUseCaseImpl implements IUpdateEntityUseCase {

    private final IEntityRepository entityRepository;

    @Override
    public Mono<Entity> execute(String id, Entity entity) {
        log.info("Updating entity with id: {}", id);
        return entityRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Entity not found with id: " + id)))
                .flatMap(existing -> {
                    entity.setId(id);
                    entity.setCreatedAt(existing.getCreatedAt());
                    return entityRepository.save(entity);
                })
                .doOnSuccess(updated -> log.info("Entity updated with id: {}", updated.getId()));
    }
}


