package pe.edu.vallegrande.sigrc.nombremicro.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.out.IEntityRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EntityRepositoryAdapter implements IEntityRepository {

    private final EntityR2dbcRepository r2dbcRepository;

    @Override
    public Mono<Entity> save(Entity entity) {
        EntityDocument document = toDocument(entity);
        if (document.getId() == null) {
            document.setCreatedAt(LocalDateTime.now());
        }
        document.setUpdatedAt(LocalDateTime.now());
        return r2dbcRepository.save(document)
                .map(this::toEntity);
    }

    @Override
    public Mono<Entity> findById(String id) {
        return r2dbcRepository.findById(Long.parseLong(id))
                .map(this::toEntity);
    }

    @Override
    public Flux<Entity> findAll() {
        return r2dbcRepository.findAll()
                .map(this::toEntity);
    }

    @Override
    public Flux<Entity> findByStatus(String status) {
        return r2dbcRepository.findByStatus(status)
                .map(this::toEntity);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return r2dbcRepository.existsById(Long.parseLong(id));
    }

    private EntityDocument toDocument(Entity entity) {
        return EntityDocument.builder()
                .id(entity.getId() != null ? Long.parseLong(entity.getId()) : null)
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private Entity toEntity(EntityDocument document) {
        return Entity.builder()
                .id(document.getId() != null ? document.getId().toString() : null)
                .name(document.getName())
                .description(document.getDescription())
                .status(document.getStatus())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }
}


