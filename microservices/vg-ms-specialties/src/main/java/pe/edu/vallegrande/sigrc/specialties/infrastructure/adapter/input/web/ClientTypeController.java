package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.specialties.domain.model.ClientType;
import pe.edu.vallegrande.sigrc.specialties.domain.port.input.ClientTypeUseCase;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request.ClientTypeRequest;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.PagedResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.ClientTypeResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.mapper.WebMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/specialties/client-types")
@RequiredArgsConstructor
public class ClientTypeController {

    private final ClientTypeUseCase useCase;
    private final WebMapper webMapper;

    @GetMapping
    public Mono<PagedResponse<ClientTypeResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        final int safeSize = size <= 0 ? 20 : size;

        return useCase.countClientTypes()
                .flatMap(total -> useCase.getClientTypesPaged(page, safeSize)
                        .map(webMapper::toResponse)
                        .collectList()
                        .map(content -> new PagedResponse<>(
                                content,
                                total,
                                (int) Math.ceil((double) total / safeSize),
                                page,
                                safeSize
                        )));
    }


    @GetMapping("/active")
    public Flux<ClientTypeResponse> getActive() {
        return useCase.getActiveClientTypes()
                .map(webMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ClientTypeResponse> getById(@PathVariable String id) {
        return useCase.getClientTypeById(id)
                .map(webMapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientTypeResponse> create(@Valid @RequestBody ClientTypeRequest request) {
        ClientType domain = webMapper.toDomain(request);
        return useCase.registerClientType(domain)
                .map(webMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ClientTypeResponse> update(@PathVariable String id, @Valid @RequestBody ClientTypeRequest request) {
        ClientType domain = webMapper.toDomain(request);
        return useCase.modifyClientType(id, domain)
                .map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/enable")
    public Mono<ClientTypeResponse> enable(@PathVariable String id) {
        return useCase.activateClientType(id)
                .map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/disable")
    public Mono<ClientTypeResponse> disable(@PathVariable String id) {
        return useCase.deactivateClientType(id)
                .map(webMapper::toResponse);
    }
}
