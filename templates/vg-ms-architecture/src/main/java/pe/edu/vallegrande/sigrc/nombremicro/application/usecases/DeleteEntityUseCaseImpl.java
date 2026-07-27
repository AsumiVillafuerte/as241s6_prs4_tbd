package pe.edu.vallegrande.sigrc.nombremicro.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.nombremicro.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in.IDeleteEntityUseCase;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.out.IEntityRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteEntityUseCaseImpl implements IDeleteEntityUseCase {

    private final IEntityRepository entityRepository;

    @Override
    public Mono<Void> execute(String id) {
        log.info("Deleting entity with id: {}", id);
        return entityRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Entity not found with id: " + id)))
                .flatMap(entity -> {
                    entity.setStatus("INACTIVE");
                    return entityRepository.save(entity);
                })
                .then()
                .doOnSuccess(v -> log.info("Entity deleted with id: {}", id));
    }
}


