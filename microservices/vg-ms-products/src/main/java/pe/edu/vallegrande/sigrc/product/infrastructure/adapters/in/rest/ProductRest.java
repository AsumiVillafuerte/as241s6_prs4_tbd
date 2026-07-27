package pe.edu.vallegrande.sigrc.product.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.sigrc.product.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.product.application.dto.request.CreateProductRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.request.UpdateProductRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.ProductResponse;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.ICreateProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IDecrementStockUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IDeleteProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IGetProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IRestoreProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IUpdateProductUseCase;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para la gestion de productos")
public class ProductRest {

    private final ICreateProductUseCase createProductUseCase;
    private final IGetProductUseCase getProductUseCase;
    private final IUpdateProductUseCase updateProductUseCase;
    private final IDeleteProductUseCase deleteProductUseCase;
    private final IRestoreProductUseCase restoreProductUseCase;
    private final IDecrementStockUseCase decrementStockUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto con estado activo (A)")
    public Mono<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody @Parameter(description = "Datos del producto a crear") CreateProductRequest request) {
        return createProductUseCase.createProduct(request)
                .map(product -> new ApiResponse<>(HttpStatus.CREATED.value(), "Producto creado exitosamente", product));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public Mono<ApiResponse<ProductResponse>> getProductById(
            @PathVariable @Parameter(description = "ID del producto") UUID id) {
        return getProductUseCase.getProductById(id)
                .map(product -> new ApiResponse<>(HttpStatus.OK.value(), "Producto encontrado", product));
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public Mono<ApiResponse<List<ProductResponse>>> getAllProducts() {
        return getProductUseCase.getAllProducts()
                .collectList()
                .map(products -> new ApiResponse<>(HttpStatus.OK.value(), "Lista de productos", products));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar productos por estado")
    public Mono<ApiResponse<List<ProductResponse>>> getProductsByStatus(
            @PathVariable @Parameter(description = "Estado (A o I)") Character status) {
        return getProductUseCase.getProductsByStatus(status)
                .collectList()
                .map(products -> new ApiResponse<>(HttpStatus.OK.value(), "Productos filtrados por estado: " + status, products));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto")
    public Mono<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable @Parameter(description = "ID del producto") UUID id,
            @Valid @RequestBody @Parameter(description = "Datos a actualizar") UpdateProductRequest request) {
        return updateProductUseCase.updateProduct(id, request)
                .map(product -> new ApiResponse<>(HttpStatus.OK.value(), "Producto actualizado exitosamente", product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar producto fisicamente")
    public Mono<Void> deleteProduct(
            @PathVariable @Parameter(description = "ID del producto") UUID id) {
        return deleteProductUseCase.deleteProduct(id);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desactivar producto (eliminacion logica)")
    public Mono<ApiResponse<Void>> deactivateProduct(
            @PathVariable @Parameter(description = "ID del producto") UUID id) {
        return deleteProductUseCase.logicalDeleteProduct(id)
                .then(Mono.just(new ApiResponse<>(HttpStatus.OK.value(), "Producto desactivado exitosamente", null)));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Reactivar producto")
    public Mono<ApiResponse<Void>> activateProduct(
            @PathVariable @Parameter(description = "ID del producto") UUID id) {
        return restoreProductUseCase.restoreProduct(id)
                .then(Mono.just(new ApiResponse<>(HttpStatus.OK.value(), "Producto reactivado exitosamente", null)));
    }

    @PatchMapping("/{id}/stock")
    @Operation(
        summary = "Decrementar stock del producto",
        description = "Reduce el stock del producto en la cantidad indicada. Falla si el stock es insuficiente."
    )
    public Mono<ApiResponse<Void>> decrementStock(
            @PathVariable @Parameter(description = "ID del producto") UUID id,
            @RequestParam @Parameter(description = "Cantidad a descontar del stock") int quantity) {
        return decrementStockUseCase.decrementStock(id, quantity)
                .then(Mono.just(new ApiResponse<>(200, "Stock actualizado exitosamente", null)));
    }
}
