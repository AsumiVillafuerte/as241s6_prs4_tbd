package pe.edu.vallegrande.sigrc.product_sale.domain.ports.in;

import pe.edu.vallegrande.sigrc.product_sale.application.dto.request.ProductSaleRequest;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductSaleResponse;
import reactor.core.publisher.Mono;

public interface ICreateProductSaleUseCase {
    Mono<ProductSaleResponse> createSale(ProductSaleRequest request);
}
