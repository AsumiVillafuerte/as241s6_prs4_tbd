package pe.edu.vallegrande.sigrc.nombremicro.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in.ICreateEntityUseCase;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.out.IEntityRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateEntityUseCaseImpl implements ICreateEntityUseCase {

    private final IEntityRepository entityRepository;

    @Override
    public Mono<Entity> execute(Entity entity) {
        log.info("Creating entity");
        entity.setStatus("ACTIVE");
        return entityRepository.save(entity)
                .doOnSuccess(saved -> log.info("Entity created with id: {}", saved.getId()));
    }
}


