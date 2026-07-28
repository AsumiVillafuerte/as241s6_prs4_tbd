package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.edu.vallegrade.sigrc.treatments.application.dto.common.ApiResponse;
import pe.edu.vallegrade.sigrc.treatments.application.dto.request.ChangeEstadoRequest;
import pe.edu.vallegrade.sigrc.treatments.application.dto.request.ChangeTipoRequest;
import pe.edu.vallegrade.sigrc.treatments.application.dto.request.CreateTratamientoRequest;
import pe.edu.vallegrade.sigrc.treatments.application.dto.request.UpdateTratamientoRequest;
import pe.edu.vallegrade.sigrc.treatments.application.dto.response.TratamientoResponse;
import pe.edu.vallegrade.sigrc.treatments.application.mappers.TratamientoMapper;
import pe.edu.vallegrade.sigrc.treatments.application.service.ComprobanteService;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IChangeEstadoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IChangeTipoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.ICreateTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IGetTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IUpdateTratamientoUseCase;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/tratamientos")
@RequiredArgsConstructor
public class TratamientoController {
        private final ICreateTratamientoUseCase createUseCase;
        private final IGetTratamientoUseCase getUseCase;
        private final IChangeTipoUseCase changeTipoUseCase;
        private final IUpdateTratamientoUseCase updateUseCase;
        private final IChangeEstadoUseCase changeEstadoUseCase;
        private final TratamientoMapper mapper;
        private final ComprobanteService comprobanteService;

        // ── POST /api/tratamientos ─────────────────────────────────
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Mono<ApiResponse<TratamientoResponse>> create(
                        @Valid @RequestBody CreateTratamientoRequest request) {

                return createUseCase.execute(mapper.toDomain(request))
                                .map(tratamiento -> ApiResponse.<TratamientoResponse>builder()
                                                .status(201)
                                                .message("Tratamiento creado correctamente")
                                                .data(mapper.toResponse(tratamiento))
                                                .build());
        }

        // ── GET /api/tratamientos ──────────────────────────────────
        @GetMapping
        public Mono<ApiResponse<java.util.List<TratamientoResponse>>> getAll() {

                return getUseCase.getAll()
                                .map(mapper::toResponse)
                                .collectList()
                                .map(lista -> ApiResponse.<java.util.List<TratamientoResponse>>builder()
                                                .status(200)
                                                .message("Listado de tratamientos")
                                                .data(lista)
                                                .build());
        }

        // ── GET /api/tratamientos/{id} ─────────────────────────────
        @GetMapping("/{id}")
        public Mono<ApiResponse<TratamientoResponse>> getById(@PathVariable String id) {

                return getUseCase.getById(id)
                                .map(tratamiento -> ApiResponse.<TratamientoResponse>builder()
                                                .status(200)
                                                .message("Tratamiento encontrado")
                                                .data(mapper.toResponse(tratamiento))
                                                .build());
        }

        // ── PUT /api/tratamientos/{id} ─────────────────────────────
        @PutMapping("/{id}")
        public Mono<ApiResponse<TratamientoResponse>> update(
                        @PathVariable String id,
                        @Valid @RequestBody UpdateTratamientoRequest request) {

                return updateUseCase.execute(id, mapper.toDomain(request))
                                .map(tratamiento -> ApiResponse.<TratamientoResponse>builder()
                                                .status(200)
                                                .message("Tratamiento actualizado correctamente")
                                                .data(mapper.toResponse(tratamiento))
                                                .build());
        }

        // ── PATCH /api/tratamientos/{id}/estado ───────────────────
        @PatchMapping("/{id}/estado")
        public Mono<ApiResponse<TratamientoResponse>> changeEstado(
                        @PathVariable String id,
                        @Valid @RequestBody ChangeEstadoRequest request) {

                return changeEstadoUseCase.execute(id, request.getEstado())
                                .map(tratamiento -> ApiResponse.<TratamientoResponse>builder()
                                                .status(200)
                                                .message("Estado actualizado correctamente")
                                                .data(mapper.toResponse(tratamiento))
                                                .build());
        }

        @PatchMapping("/{id}/tipo")
        public Mono<ApiResponse<TratamientoResponse>> changeTipo(
                        @PathVariable String id,
                        @Valid @RequestBody ChangeTipoRequest request) {

                return changeTipoUseCase.execute(id, request.getTipo())
                                .map(tratamiento -> ApiResponse.<TratamientoResponse>builder()
                                                .status(200)
                                                .message("Tipo actualizado correctamente")
                                                .data(mapper.toResponse(tratamiento))
                                                .build());
        }

        @GetMapping("/{id}/comprobante")
        public Mono<ResponseEntity<byte[]>> getComprobante(@PathVariable String id) {
                return comprobanteService.generarComprobante(id)
                                .map(bytes -> ResponseEntity.ok()
                                                .header("Content-Disposition",
                                                                "attachment; filename=\"comprobante-" + id + ".pdf\"")
                                                .header("Content-Type", "application/pdf")
                                                .body(bytes));
        }
}
