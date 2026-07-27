package pe.edu.vallegrande.sigrc.product.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.product.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.product.application.dto.request.CreateBrandRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.request.UpdateBrandRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.BrandResponse;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.ICreateBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IDeleteBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IGetBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IRestoreBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IUpdateBrandUseCase;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "API para la gestion de marcas")
public class BrandRest {

    private final ICreateBrandUseCase createBrandUseCase;
    private final IGetBrandUseCase getBrandUseCase;
    private final IUpdateBrandUseCase updateBrandUseCase;
    private final IDeleteBrandUseCase deleteBrandUseCase;
    private final IRestoreBrandUseCase restoreBrandUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva marca", description = "Crea una nueva marca con estado activo (A)")
    public Mono<ApiResponse<BrandResponse>> createBrand(
            @Valid @RequestBody @Parameter(description = "Datos de la marca a crear") CreateBrandRequest request) {
        return createBrandUseCase.createBrand(request)
                .map(brand -> new ApiResponse<>(HttpStatus.CREATED.value(), "Marca creada exitosamente", brand));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener marca por ID")
    public Mono<ApiResponse<BrandResponse>> getBrandById(
            @PathVariable @Parameter(description = "ID de la marca") UUID id) {
        return getBrandUseCase.getBrandById(id)
                .map(brand -> new ApiResponse<>(HttpStatus.OK.value(), "Marca encontrada", brand));
    }

    @GetMapping
    @Operation(summary = "Listar todas las marcas")
    public Mono<ApiResponse<List<BrandResponse>>> getAllBrands() {
        return getBrandUseCase.getAllBrands()
                .collectList()
                .map(brands -> new ApiResponse<>(HttpStatus.OK.value(), "Lista de marcas", brands));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar marcas por estado")
    public Mono<ApiResponse<List<BrandResponse>>> getBrandsByStatus(
            @PathVariable @Parameter(description = "Estado (A o I)") Character status) {
        return getBrandUseCase.getBrandsByStatus(status)
                .collectList()
                .map(brands -> new ApiResponse<>(HttpStatus.OK.value(), "Marcas filtradas por estado: " + status, brands));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar marca")
    public Mono<ApiResponse<BrandResponse>> updateBrand(
            @PathVariable @Parameter(description = "ID de la marca") UUID id,
            @Valid @RequestBody @Parameter(description = "Datos a actualizar") UpdateBrandRequest request) {
        return updateBrandUseCase.updateBrand(id, request)
                .map(brand -> new ApiResponse<>(HttpStatus.OK.value(), "Marca actualizada exitosamente", brand));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar marca fisicamente")
    public Mono<Void> deleteBrand(
            @PathVariable @Parameter(description = "ID de la marca") UUID id) {
        return deleteBrandUseCase.deleteBrand(id);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desactivar marca (eliminacion logica)")
    public Mono<ApiResponse<Void>> deactivateBrand(
            @PathVariable @Parameter(description = "ID de la marca") UUID id) {
        return deleteBrandUseCase.logicalDeleteBrand(id)
                .then(Mono.just(new ApiResponse<>(HttpStatus.OK.value(), "Marca desactivada exitosamente", null)));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Reactivar marca")
    public Mono<ApiResponse<Void>> activateBrand(
            @PathVariable @Parameter(description = "ID de la marca") UUID id) {
        return restoreBrandUseCase.restoreBrand(id)
                .then(Mono.just(new ApiResponse<>(HttpStatus.OK.value(), "Marca reactivada exitosamente", null)));
    }
}
