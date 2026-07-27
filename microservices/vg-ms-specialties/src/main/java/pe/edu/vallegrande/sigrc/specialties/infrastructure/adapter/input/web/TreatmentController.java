package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.specialties.domain.port.input.TreatmentUseCase;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request.TreatmentRequest;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.PagedResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.TreatmentResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.mapper.WebMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/specialties/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentUseCase useCase;
    private final WebMapper webMapper;

    @GetMapping
    public Mono<PagedResponse<TreatmentResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        final int safeSize = size <= 0 ? 20 : size;    
        
        return useCase.countTreatments()
                .flatMap(total -> useCase.getTreatmentsPaged(page, size)
                        .map(webMapper::toResponse)
                        .collectList()
                        .map(content -> new PagedResponse<>(
                                content,
                                total,
                                (int) Math.ceil((double) total / safeSize),
                                page,
                                size
                        )));
    }

    @GetMapping("/{id}")
    public Mono<TreatmentResponse> getById(@PathVariable String id) {
        return useCase.getTreatmentById(id)
                .map(webMapper::toResponse);
    }

    @GetMapping("/specialty/{specialtyId}")
    public Flux<TreatmentResponse> getBySpecialty(@PathVariable String specialtyId) {
        return useCase.getTreatmentsBySpecialty(specialtyId)
                .map(webMapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TreatmentResponse> create(@Valid @RequestBody TreatmentRequest request) {
        Treatment domain = webMapper.toDomain(request);
        return useCase.registerTreatment(domain)
                .map(webMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<TreatmentResponse> update(@PathVariable String id, @Valid @RequestBody TreatmentRequest request) {
        Treatment domain = webMapper.toDomain(request);
        return useCase.modifyTreatment(id, domain)
                .map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/enable")
    public Mono<TreatmentResponse> enable(@PathVariable String id) {
        return useCase.activateTreatment(id)
                .map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/disable")
    public Mono<TreatmentResponse> disable(@PathVariable String id) {
        return useCase.deactivateTreatment(id)
                .map(webMapper::toResponse);
    }
}
