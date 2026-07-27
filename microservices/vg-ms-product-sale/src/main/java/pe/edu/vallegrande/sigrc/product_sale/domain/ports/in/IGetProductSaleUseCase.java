package pe.edu.vallegrande.sigrc.product_sale.domain.ports.in;

import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductSaleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IGetProductSaleUseCase {
    Mono<ProductSaleResponse> getSaleById(UUID id);
    Flux<ProductSaleResponse> getAllSales();
    Flux<ProductSaleResponse> getSalesByStatus(String status);
}
