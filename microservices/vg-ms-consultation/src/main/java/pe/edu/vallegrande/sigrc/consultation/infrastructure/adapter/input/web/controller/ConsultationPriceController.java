package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.consultation.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.consultation.domain.model.ConsultationPrice;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.ConsultationPriceUseCase;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.request.ConsultationPriceRequest;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response.PagedResponse;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response.ConsultationPriceResponse;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.mapper.WebMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/consultations/consultation-prices")
@RequiredArgsConstructor
public class ConsultationPriceController {

    private final ConsultationPriceUseCase useCase;
    private final WebMapper webMapper;

    @GetMapping
    public Mono<PagedResponse<ConsultationPriceResponse>> getAll(
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

    @GetMapping("/by-specialty/{specialtyId}")
    public Flux<ConsultationPriceResponse> getBySpecialty(@PathVariable String specialtyId) {
        return useCase.getBySpecialty(specialtyId).map(webMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ConsultationPriceResponse> getById(@PathVariable String id) {
        return useCase.getById(id)
                .map(webMapper::toResponse)
                .switchIfEmpty(Mono.error(new NotFoundException("ConsultationPrice not found with id: " + id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ConsultationPriceResponse> create(@Valid @RequestBody ConsultationPriceRequest request) {
        ConsultationPrice domain = webMapper.toDomain(request);
        return useCase.register(domain).map(webMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ConsultationPriceResponse> update(@PathVariable String id, @Valid @RequestBody ConsultationPriceRequest request) {
        ConsultationPrice domain = webMapper.toDomain(request);
        return useCase.modify(id, domain).map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/deactivate")
    public Mono<ConsultationPriceResponse> deactivate(@PathVariable String id) {
        return useCase.deactivate(id).map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/restore")
    public Mono<ConsultationPriceResponse> restore(@PathVariable String id) {
        return useCase.activate(id).map(webMapper::toResponse);
    }
}
