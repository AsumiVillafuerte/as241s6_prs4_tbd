package pe.edu.vallegrande.sigrc.nombremicro.application.mappers;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.nombremicro.application.dto.request.CreateEntityRequest;
import pe.edu.vallegrande.sigrc.nombremicro.application.dto.request.UpdateEntityRequest;
import pe.edu.vallegrande.sigrc.nombremicro.application.dto.response.EntityResponse;
import pe.edu.vallegrande.sigrc.nombremicro.domain.models.Entity;

@Component
public class EntityMapper {

    public Entity toEntity(CreateEntityRequest request) {
        return Entity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status("ACTIVE")
                .build();
    }

    public Entity toEntity(UpdateEntityRequest request) {
        return Entity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public EntityResponse toResponse(Entity entity) {
        return EntityResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}


