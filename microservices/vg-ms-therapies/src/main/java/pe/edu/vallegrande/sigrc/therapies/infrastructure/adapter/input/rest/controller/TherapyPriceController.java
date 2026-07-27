package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.controller;

import jakarta.validation.Valid;
import pe.edu.vallegrande.sigrc.therapies.application.service.TherapyPriceService;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.TherapyPriceRequest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.PagedResponse;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.TherapyPriceResponse;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper.WebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/therapies/therapy-prices")
public class TherapyPriceController {

    private final TherapyPriceService service;
    private final WebMapper mapper;

    public TherapyPriceController(TherapyPriceService service, WebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Mono<PagedResponse<TherapyPriceResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return service.countAll()
                .flatMap(total -> service.findAllPaged(page, size)
                        .map(mapper::toResponse)
                        .collectList()
                        .map(content -> PagedResponse.<TherapyPriceResponse>builder()
                                .content(content)
                                .totalElements(total)
                                .totalPages((int) Math.ceil((double) total / size))
                                .number(page)
                                .size(size)
                                .build()));
    }

    @GetMapping("/by-specialty/{specialtyId}")
    public Flux<TherapyPriceResponse> getBySpecialty(@PathVariable String specialtyId) {
        return service.findBySpecialtyId(specialtyId).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<TherapyPriceResponse> getById(@PathVariable String id) {
        return service.findById(id).map(mapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TherapyPriceResponse> create(@Valid @RequestBody TherapyPriceRequest request) {
        return service.create(mapper.toDomain(request)).map(mapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<TherapyPriceResponse> update(@PathVariable String id, @Valid @RequestBody TherapyPriceRequest request) {
        return service.update(id, mapper.toDomain(request)).map(mapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }

    @PatchMapping("/{id}/deactivate")
    public Mono<TherapyPriceResponse> deactivate(@PathVariable String id) {
        return service.deactivate(id).map(mapper::toResponse);
    }

    @PatchMapping("/{id}/restore")
    public Mono<TherapyPriceResponse> restore(@PathVariable String id) {
        return service.restore(id).map(mapper::toResponse);
    }
}
