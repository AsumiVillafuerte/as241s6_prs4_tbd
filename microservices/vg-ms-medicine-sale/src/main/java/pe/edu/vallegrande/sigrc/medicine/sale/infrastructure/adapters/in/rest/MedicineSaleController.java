package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.medicine.sale.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.medicine.sale.application.dto.request.MedicineSaleRequest;
import pe.edu.vallegrande.sigrc.medicine.sale.application.dto.response.MedicineSaleResponse;
import pe.edu.vallegrande.sigrc.medicine.sale.application.mappers.MedicineSaleMapper;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.ICreateMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.IDeleteMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.IGetMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.IUpdateMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleType;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicine/sales")
@Tag(name = "Medicine Sales", description = "API para la gestión de ventas de medicamentos - Cáritas")
public class MedicineSaleController {

    private final IGetMedicineSaleUseCase getUseCase;
    private final ICreateMedicineSaleUseCase createUseCase;
    private final IUpdateMedicineSaleUseCase updateUseCase;
    private final IDeleteMedicineSaleUseCase deleteUseCase;

    // ── OPCIONES PARA FRONTEND ───────────────────────────────────────────────

    @GetMapping("/options")
    @Operation(summary = "Obtener opciones de tipo y estado para los desplegables del frontend")
    public Mono<ApiResponse<Map<String, Object>>> getOptions() {
        List<String> types = List.of(SaleType.VENDIDO.name(), SaleType.DONADO.name());
        List<String> statuses = List.of(SaleStatus.CONSIGNADO.name());
        return Mono.just(ApiResponse.success("Opciones disponibles",
                Map.of("types", types, "statuses", statuses)));
    }

    @GetMapping
    @Operation(summary = "Listar todas las ventas activas (excluye las eliminadas)")    public Mono<ApiResponse<List<MedicineSaleResponse>>> findAll() {
        return getUseCase.findAll()
                .map(MedicineSaleMapper::toResponse)
                .collectList()
                .map(list -> ApiResponse.success("Lista de ventas", list));
    }

    @GetMapping("/inactive")
    @Operation(summary = "Listar ventas eliminadas lógicamente")
    public Mono<ApiResponse<List<MedicineSaleResponse>>> findAllInactive() {
        return getUseCase.findAllInactive()
                .map(MedicineSaleMapper::toResponse)
                .collectList()
                .map(list -> ApiResponse.success("Lista de ventas eliminadas", list));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar ventas por estado (VENDIDO, CONSIGNADO, DONADO, REVOCADO)")
    public Mono<ApiResponse<List<MedicineSaleResponse>>> findByStatus(
            @Parameter(description = "Estado: VENDIDO | CONSIGNADO | DONADO | REVOCADO")
            @PathVariable String status) {
        return getUseCase.findByStatus(status)
                .map(MedicineSaleMapper::toResponse)
                .collectList()
                .map(list -> ApiResponse.success("Lista de ventas por estado", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID")
    public Mono<ApiResponse<MedicineSaleResponse>> findById(@PathVariable String id) {
        return getUseCase.findById(id)
                .map(MedicineSaleMapper::toResponse)
                .map(r -> ApiResponse.success("Venta encontrada", r));
    }

    @GetMapping("/ticket/{ticket}")
    @Operation(summary = "Obtener venta por número de ticket")
    public Mono<ApiResponse<MedicineSaleResponse>> findByTicket(@PathVariable String ticket) {
        return getUseCase.findByTicket(ticket)
                .map(MedicineSaleMapper::toResponse)
                .map(r -> ApiResponse.success("Venta encontrada", r));
    }

    @GetMapping("/patient/{dni}")
    @Operation(summary = "Listar historial de ventas por DNI del paciente")
    public Mono<ApiResponse<List<MedicineSaleResponse>>> findByDni(@PathVariable String dni) {
        return getUseCase.findByDni(dni)
                .map(MedicineSaleMapper::toResponse)
                .collectList()
                .map(list -> ApiResponse.success("Ventas del paciente", list));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar nueva venta de medicamentos")
    public Mono<ApiResponse<MedicineSaleResponse>> create(@Valid @RequestBody MedicineSaleRequest request) {
        return createUseCase.create(MedicineSaleMapper.toDomain(request))
                .map(MedicineSaleMapper::toResponse)
                .map(ApiResponse::created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de una venta existente")
    public Mono<ApiResponse<MedicineSaleResponse>> update(
            @PathVariable String id,
            @Valid @RequestBody MedicineSaleRequest request) {
        return updateUseCase.update(id, MedicineSaleMapper.toDomain(request))
                .map(MedicineSaleMapper::toResponse)
                .map(r -> ApiResponse.success("Venta actualizada", r));
    }

    @PatchMapping("/{id}/revoke")
    @Operation(summary = "Revocar una venta (cambia estado a REVOCADO)")
    public Mono<ApiResponse<MedicineSaleResponse>> revoke(@PathVariable String id) {
        return deleteUseCase.revoke(id)
                .map(MedicineSaleMapper::toResponse)
                .map(r -> ApiResponse.success("Venta revocada", r));
    }

    @PatchMapping("/{id}/mark-as-paid")
    @Operation(summary = "Marcar venta CONSIGNADA como donación (cambia tipo a DONADO)")
    public Mono<ApiResponse<MedicineSaleResponse>> markAsPaid(@PathVariable String id) {
        return deleteUseCase.markAsPaid(id)
                .map(MedicineSaleMapper::toResponse)
                .map(r -> ApiResponse.success("Venta marcada como donación", r));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminado lógico (cambia estado a INACTIVE)")
    public Mono<ApiResponse<MedicineSaleResponse>> delete(@PathVariable String id) {
        return deleteUseCase.delete(id)
                .map(MedicineSaleMapper::toResponse)
                .map(r -> ApiResponse.success("Venta eliminada", r));
    }

    @PatchMapping("/{id}/restore")
    @Operation(summary = "Restaurar una venta eliminada o revocada (cambia estado a CONSIGNADO)")
    public Mono<ApiResponse<MedicineSaleResponse>> restore(@PathVariable String id) {
        return deleteUseCase.restore(id)
                .map(MedicineSaleMapper::toResponse)
                .map(r -> ApiResponse.success("Venta restaurada", r));
    }
}
