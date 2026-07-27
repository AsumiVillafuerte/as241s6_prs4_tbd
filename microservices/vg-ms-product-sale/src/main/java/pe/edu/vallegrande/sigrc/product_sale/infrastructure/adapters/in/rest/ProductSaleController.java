package pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.common.ApiResponse;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.request.ProductSaleRequest;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.request.SaleTypeRequest;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductItemResponse;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductSaleResponse;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.ICreateProductSaleUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.IDeleteProductSaleUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.IGetProductSaleUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.IUpdateSaleTypeUseCase;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class ProductSaleController {

    private final ICreateProductSaleUseCase createSaleUseCase;
    private final IGetProductSaleUseCase getSaleUseCase;
    private final IDeleteProductSaleUseCase deleteSaleUseCase;
    private final IUpdateSaleTypeUseCase updateSaleTypeUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApiResponse<ProductSaleResponse>> createSale(
            @Valid @RequestBody ProductSaleRequest request) {
        return createSaleUseCase.createSale(request)
                .map(sale -> new ApiResponse<>(HttpStatus.CREATED.value(), "Venta creada exitosamente", sale));
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<ProductSaleResponse>> getSaleById(@PathVariable UUID id) {
        return getSaleUseCase.getSaleById(id)
                .map(sale -> new ApiResponse<>(HttpStatus.OK.value(), "Venta encontrada", sale));
    }

    @GetMapping
    public Mono<ApiResponse<List<ProductSaleResponse>>> getAllSales() {
        return getSaleUseCase.getAllSales()
                .collectList()
                .map(sales -> new ApiResponse<>(HttpStatus.OK.value(), "Lista de ventas", sales));
    }

    @GetMapping("/status/{status}")
    public Mono<ApiResponse<List<ProductSaleResponse>>> getSalesByStatus(@PathVariable String status) {
        return getSaleUseCase.getSalesByStatus(status)
                .collectList()
                .map(sales -> new ApiResponse<>(HttpStatus.OK.value(),
                        "Ventas filtradas por estado: " + status, sales));
    }

    @GetMapping("/{id}/items")
    public Mono<ApiResponse<List<ProductItemResponse>>> getSaleItems(@PathVariable UUID id) {
        return getSaleUseCase.getSaleById(id)
                .map(sale -> new ApiResponse<>(HttpStatus.OK.value(), "Items de la venta", sale.getItems()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSale(@PathVariable UUID id) {
        return deleteSaleUseCase.deleteSale(id);
    }

    @PatchMapping("/{id}/revoke")
    public Mono<ApiResponse<Void>> revokeSale(@PathVariable UUID id) {
        return deleteSaleUseCase.revokeSale(id)
                .then(Mono.just(new ApiResponse<>(HttpStatus.OK.value(), "Venta anulada exitosamente", null)));
    }

    @PatchMapping("/{id}/restore")
    public Mono<ApiResponse<Void>> restoreSale(@PathVariable UUID id) {
        return deleteSaleUseCase.restoreSale(id)
                .then(Mono.just(new ApiResponse<>(HttpStatus.OK.value(), "Venta restaurada exitosamente", null)));
    }

    @PatchMapping("/{id}/type")
    public Mono<ApiResponse<Void>> updateSaleType(
            @PathVariable UUID id,
            @Valid @RequestBody SaleTypeRequest request) {
        return updateSaleTypeUseCase.updateSaleType(id, request.getSaleType())
                .then(Mono.just(new ApiResponse<>(HttpStatus.OK.value(), "Tipo de venta actualizado exitosamente", null)));
    }
}
