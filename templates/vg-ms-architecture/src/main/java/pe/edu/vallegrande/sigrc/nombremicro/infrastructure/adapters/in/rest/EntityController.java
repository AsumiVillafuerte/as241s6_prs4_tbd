package pe.edu.vallegrande.sigrc.nombremicro.infrastructure.adapters.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.nombremicro.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.nombremicro.application.dto.request.CreateEntityRequest;
import pe.edu.vallegrande.sigrc.nombremicro.application.dto.request.UpdateEntityRequest;
import pe.edu.vallegrande.sigrc.nombremicro.application.dto.response.EntityResponse;
import pe.edu.vallegrande.sigrc.nombremicro.application.mappers.EntityMapper;
import pe.edu.vallegrande.sigrc.nombremicro.domain.ports.in.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
public class EntityController {

    private final ICreateEntityUseCase createEntityUseCase;
    private final IGetEntityUseCase getEntityUseCase;
    private final IUpdateEntityUseCase updateEntityUseCase;
    private final IDeleteEntityUseCase deleteEntityUseCase;
    private final EntityMapper entityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApiResponse<EntityResponse>> create(@Valid @RequestBody CreateEntityRequest request) {
        return createEntityUseCase.execute(entityMapper.toEntity(request))
                .map(entityMapper::toResponse)
                .map(response -> ApiResponse.<EntityResponse>builder()
                        .success(true)
                        .message("Entity created successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<EntityResponse>> findById(@PathVariable String id) {
        return getEntityUseCase.findById(id)
                .map(entityMapper::toResponse)
                .map(response -> ApiResponse.<EntityResponse>builder()
                        .success(true)
                        .message("Entity found")
                        .data(response)
                        .build());
    }

    @GetMapping
    public Flux<EntityResponse> findAll(@RequestParam(required = false) String status) {
        if (status != null) {
            return getEntityUseCase.findByStatus(status)
                    .map(entityMapper::toResponse);
        }
        return getEntityUseCase.findAll()
                .map(entityMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<EntityResponse>> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateEntityRequest request) {
        return updateEntityUseCase.execute(id, entityMapper.toEntity(request))
                .map(entityMapper::toResponse)
                .map(response -> ApiResponse.<EntityResponse>builder()
                        .success(true)
                        .message("Entity updated successfully")
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return deleteEntityUseCase.execute(id);
    }
}


