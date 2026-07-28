package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.consultation.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.consultation.domain.model.Consultation;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.ConsultationUseCase;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.request.ConsultationRequest;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response.PagedResponse;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response.ConsultationResponse;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.mapper.WebMapper;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationUseCase useCase;
    private final WebMapper webMapper;

    @GetMapping
    public Mono<PagedResponse<ConsultationResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        final int safeSize = size <= 0 ? 20 : size;
        return useCase.count()
                .flatMap(total -> useCase.getPaged(page, safeSize)
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

    @GetMapping("/{id}")
    public Mono<ConsultationResponse> getById(@PathVariable String id) {
        return useCase.getById(id)
                .map(webMapper::toResponse)
                .switchIfEmpty(Mono.error(new NotFoundException("Consultation not found with id: " + id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ConsultationResponse> create(@Valid @RequestBody ConsultationRequest request) {
        Consultation domain = webMapper.toDomain(request);
        return useCase.register(domain).map(webMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ConsultationResponse> update(@PathVariable String id, @Valid @RequestBody ConsultationRequest request) {
        Consultation domain = webMapper.toDomain(request);
        return useCase.modify(id, domain).map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/deactivate")
    public Mono<ConsultationResponse> deactivate(@PathVariable String id) {
        return useCase.cancel(id).map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/restore")
    public Mono<ConsultationResponse> restore(@PathVariable String id) {
        return useCase.restore(id).map(webMapper::toResponse);
    }
}
