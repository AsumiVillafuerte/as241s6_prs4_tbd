package pe.edu.vallegrande.sigrc.medications.infrastructure.adapters.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.medications.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.medications.application.dto.request.CreateMedicationRequest;
import pe.edu.vallegrande.sigrc.medications.application.dto.request.UpdateMedicationRequest;
import pe.edu.vallegrande.sigrc.medications.application.dto.response.MedicationResponse;
import pe.edu.vallegrande.sigrc.medications.application.mappers.MedicationMapper;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.ICreateMedicationUseCase;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.IDeleteMedicationUseCase;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.IGetMedicationUseCase;
import pe.edu.vallegrande.sigrc.medications.domain.ports.in.IUpdateMedicationUseCase;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final IGetMedicationUseCase getUseCase;
    private final ICreateMedicationUseCase createUseCase;
    private final IUpdateMedicationUseCase updateUseCase;
    private final IDeleteMedicationUseCase deleteUseCase;

    @GetMapping
    public Mono<ApiResponse<List<MedicationResponse>>> getAll() {
        return getUseCase.getAll()
                .map(MedicationMapper::toResponse)
                .collectList()
                .map(list -> ApiResponse.success("Lista de medicamentos", list));
    }

    @GetMapping("/inactive")
    public Mono<ApiResponse<List<MedicationResponse>>> getAllInactive() {
        return getUseCase.getAllInactive()
                .map(MedicationMapper::toResponse)
                .collectList()
                .map(list -> ApiResponse.success("Lista de medicamentos inactivos", list));
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<MedicationResponse>> getById(@PathVariable String id) {
        return getUseCase.getById(id)
                .map(MedicationMapper::toResponse)
                .map(r -> ApiResponse.success("Medicamento encontrado", r));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApiResponse<MedicationResponse>> create(@Valid @RequestBody CreateMedicationRequest request) {
        return createUseCase.create(MedicationMapper.toDomain(request))
                .map(MedicationMapper::toResponse)
                .map(ApiResponse::created);
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<MedicationResponse>> update(@PathVariable String id,
                                                         @Valid @RequestBody UpdateMedicationRequest request) {
        return updateUseCase.update(id, MedicationMapper.toDomain(request))
                .map(MedicationMapper::toResponse)
                .map(r -> ApiResponse.success("Medicamento actualizado", r));
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<MedicationResponse>> delete(@PathVariable String id) {
        return deleteUseCase.delete(id)
                .map(MedicationMapper::toResponse)
                .map(r -> ApiResponse.success("Medicamento desactivado", r));
    }

    @PatchMapping("/{id}/restore")
    public Mono<ApiResponse<MedicationResponse>> restore(@PathVariable String id) {
        return deleteUseCase.restore(id)
                .map(MedicationMapper::toResponse)
                .map(r -> ApiResponse.success("Medicamento restaurado", r));
    }
}
