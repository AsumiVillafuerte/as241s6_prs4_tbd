package pe.edu.vallegrande.sigrc.suppliers.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.suppliers.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.suppliers.application.dto.request.CreateSupplierRequest;
import pe.edu.vallegrande.sigrc.suppliers.application.dto.request.UpdateSupplierRequest;
import pe.edu.vallegrande.sigrc.suppliers.application.dto.response.SupplierResponse;
import pe.edu.vallegrande.sigrc.suppliers.application.mappers.SupplierMapper;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.ICreateSupplierUseCase;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.IDeleteSupplierUseCase;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.IGetSupplierUseCase;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.IUpdateSupplierUseCase;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Proveedores", description = "Gestión del catálogo maestro de proveedores")
@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final ICreateSupplierUseCase createUseCase;
    private final IGetSupplierUseCase getUseCase;
    private final IUpdateSupplierUseCase updateUseCase;
    private final IDeleteSupplierUseCase deleteUseCase;

    public SupplierController(ICreateSupplierUseCase createUseCase,
                              IGetSupplierUseCase getUseCase,
                              IUpdateSupplierUseCase updateUseCase,
                              IDeleteSupplierUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @Operation(summary = "Crear proveedor", description = "Registra un nuevo proveedor en el sistema")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio violada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Documento o email duplicado")
    })
    @PostMapping
    public Mono<ResponseEntity<ApiResponse<SupplierResponse>>> create(@Valid @RequestBody CreateSupplierRequest request) {
        return createUseCase.create(SupplierMapper.toDomain(request))
                .map(supplier -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.ok("Proveedor creado exitosamente", SupplierMapper.toResponse(supplier))));
    }

    @Operation(summary = "Listar proveedores", description = "Retorna todos los proveedores registrados")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<SupplierResponse>>>> findAll() {
        return getUseCase.findAll()
                .map(SupplierMapper::toResponse)
                .collectList()
                .map(list -> ResponseEntity.ok(ApiResponse.ok("Proveedores obtenidos exitosamente", list)));
    }

    @Operation(summary = "Obtener proveedor por ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<SupplierResponse>>> findById(@PathVariable Long id) {
        return getUseCase.findById(id)
                .map(supplier -> ResponseEntity.ok(
                        ApiResponse.ok("Proveedor obtenido exitosamente", SupplierMapper.toResponse(supplier))));
    }

    @Operation(summary = "Actualizar proveedor", description = "Actualiza los datos de un proveedor existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<SupplierResponse>>> update(@PathVariable Long id,
                                                                      @Valid @RequestBody UpdateSupplierRequest request) {
        return updateUseCase.update(id, SupplierMapper.toDomain(request))
                .map(supplier -> ResponseEntity.ok(
                        ApiResponse.ok("Proveedor actualizado exitosamente", SupplierMapper.toResponse(supplier))));
    }

    @Operation(summary = "Desactivar proveedor", description = "Cambia el estado del proveedor a inactivo (status=false)")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Proveedor desactivado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @PatchMapping("/{id}/deactivate")
    public Mono<ResponseEntity<Void>> deactivate(@PathVariable Long id) {
        return deleteUseCase.deactivate(id)
                .then(Mono.just(ResponseEntity.<Void>noContent().build()));
    }

    @Operation(summary = "Restaurar proveedor", description = "Cambia el estado del proveedor a activo (status=true)")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Proveedor restaurado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @PatchMapping("/{id}/restore")
    public Mono<ResponseEntity<Void>> restore(@PathVariable Long id) {
        return deleteUseCase.restore(id)
                .then(Mono.just(ResponseEntity.<Void>noContent().build()));
    }

    @Operation(summary = "Filtrar por estado", description = "Retorna proveedores activos (true) o inactivos (false)")
    @GetMapping("/status/{status}")
    public Mono<ResponseEntity<ApiResponse<List<SupplierResponse>>>> findByStatus(@PathVariable Boolean status) {
        return getUseCase.findByStatus(status)
                .map(SupplierMapper::toResponse)
                .collectList()
                .map(list -> ResponseEntity.ok(ApiResponse.ok("Proveedores filtrados por estado", list)));
    }

    @Operation(summary = "Filtrar por tipo de documento", description = "Retorna proveedores por tipo: RUC o DNI")
    @GetMapping("/document-type/{type}")
    public Mono<ResponseEntity<ApiResponse<List<SupplierResponse>>>> findByDocumentType(@PathVariable String type) {
        return getUseCase.findByDocumentType(type)
                .map(SupplierMapper::toResponse)
                .collectList()
                .map(list -> ResponseEntity.ok(ApiResponse.ok("Proveedores filtrados por tipo de documento", list)));
    }
}
