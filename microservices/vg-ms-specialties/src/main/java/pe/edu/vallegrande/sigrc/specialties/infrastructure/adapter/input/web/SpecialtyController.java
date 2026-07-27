package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Specialty;
import pe.edu.vallegrande.sigrc.specialties.domain.port.input.SpecialtyUseCase;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request.SpecialtyRequest;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.PagedResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.SpecialtyResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.mapper.WebMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyUseCase useCase;
    private final WebMapper webMapper;

    @GetMapping
    public Mono<PagedResponse<SpecialtyResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        final int safeSize = size <= 0 ? 20 : size;      

        return useCase.countSpecialties()
                .flatMap(total -> useCase.getSpecialtiesPaged(page, size)
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
    public Mono<SpecialtyResponse> getById(@PathVariable String id) {
        return useCase.getSpecialtyById(id)
                .map(webMapper::toResponse);
    }

    @GetMapping("/status/{status}")
    public Flux<SpecialtyResponse> getByStatus(@PathVariable String status) {
        CommonStatus commonStatus = CommonStatus.fromString(status);
        return useCase.getSpecialtiesByStatus(commonStatus)
                .map(webMapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SpecialtyResponse> create(@Valid @RequestBody SpecialtyRequest request) {
        Specialty domain = webMapper.toDomain(request);
        return useCase.registerSpecialty(domain)
                .map(webMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<SpecialtyResponse> update(@PathVariable String id, @Valid @RequestBody SpecialtyRequest request) {
        Specialty domain = webMapper.toDomain(request);
        return useCase.modifySpecialty(id, domain)
                .map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/enable")
    public Mono<SpecialtyResponse> enable(@PathVariable String id) {
        return useCase.activateSpecialty(id)
                .map(webMapper::toResponse);
    }

    @PatchMapping("/{id}/disable")
    public Mono<SpecialtyResponse> disable(@PathVariable String id) {
        return useCase.deactivateSpecialty(id)
                .map(webMapper::toResponse);
    }
}
