package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.controller;

import jakarta.validation.Valid;
import pe.edu.vallegrande.sigrc.therapies.application.service.LabTestService;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.LabTestRequest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.PagedResponse;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.LabTestResponse;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper.WebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/therapies/laboratory/tests")
public class LabTestController {

    private final LabTestService service;
    private final WebMapper mapper;

    public LabTestController(LabTestService service, WebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Mono<PagedResponse<LabTestResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return service.countAll()
                .flatMap(total -> service.findAllPaged(page, size)
                        .map(mapper::toResponse)
                        .collectList()
                        .map(content -> PagedResponse.<LabTestResponse>builder()
                                .content(content)
                                .totalElements(total)
                                .totalPages((int) Math.ceil((double) total / size))
                                .number(page)
                                .size(size)
                                .build()));
    }

    @GetMapping("/{id}")
    public Mono<LabTestResponse> getById(@PathVariable String id) {
        return service.findById(id).map(mapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LabTestResponse> create(@Valid @RequestBody LabTestRequest request) {
        return service.create(mapper.toDomain(request)).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<LabTestResponse> update(@PathVariable String id, @Valid @RequestBody LabTestRequest request) {
        return service.update(id, mapper.toDomain(request)).map(mapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PatchMapping("/{id}/deactivate")
    public Mono<LabTestResponse> deactivate(@PathVariable String id) {
        return service.deactivate(id).map(mapper::toResponse);
    }

    @PatchMapping("/{id}/restore")
    public Mono<LabTestResponse> restore(@PathVariable String id) {
        return service.restore(id).map(mapper::toResponse);
    }
}
