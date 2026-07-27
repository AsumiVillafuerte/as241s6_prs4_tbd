package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.shopping.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.shopping.application.dto.request.CreateCompraRequest;
import pe.edu.vallegrande.sigrc.shopping.application.dto.response.CompraListItemResponse;
import pe.edu.vallegrande.sigrc.shopping.application.dto.response.CompraResponse;
import pe.edu.vallegrande.sigrc.shopping.application.mapper.CompraMapper;
import pe.edu.vallegrande.sigrc.shopping.domain.port.in.ICreateCompraUseCase;
import pe.edu.vallegrande.sigrc.shopping.domain.port.in.IGetCompraUseCase;
import pe.edu.vallegrande.sigrc.shopping.domain.port.in.IListCompraUseCase;
import pe.edu.vallegrande.sigrc.shopping.domain.port.in.IRevokeCompraUseCase;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping")
@RequiredArgsConstructor
@Tag(name = "Compras", description = "Gestión de compras de medicamentos")
public class CompraController {

    private final ICreateCompraUseCase createUseCase;
    private final IGetCompraUseCase getUseCase;
    private final IListCompraUseCase listUseCase;
    private final IRevokeCompraUseCase revokeUseCase;
    private final CompraMapper compraMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar una nueva compra de medicamentos")
    public Mono<ApiResponse<CompraResponse>> create(@Valid @RequestBody CreateCompraRequest request) {
        return createUseCase.execute(compraMapper.toDomain(request, request.usuarioId()))
                .map(compra -> ApiResponse.ok("Compra registrada exitosamente", compraMapper.toResponse(compra)));
    }

    @GetMapping
    @Operation(summary = "Listar todas las compras")
    public Mono<ApiResponse<List<CompraListItemResponse>>> listAll() {
        return listUseCase.execute()
                .map(compraMapper::toListItem)
                .collectList()
                .map(list -> ApiResponse.ok("Compras obtenidas exitosamente", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de una compra por ID")
    public Mono<ApiResponse<CompraResponse>> getById(@PathVariable Long id) {
        return getUseCase.execute(id)
                .map(compra -> ApiResponse.ok("Compra encontrada", compraMapper.toResponse(compra)));
    }

    @PatchMapping("/{id}/revoke")
    @Operation(summary = "Revocar una compra (desactiva sus lotes en ms-medications)")
    public Mono<ApiResponse<CompraResponse>> revoke(@PathVariable Long id) {
        return revokeUseCase.execute(id)
                .map(compra -> ApiResponse.ok("Compra revocada exitosamente", compraMapper.toResponse(compra)));
    }
}
