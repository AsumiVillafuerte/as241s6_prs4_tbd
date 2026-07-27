package pe.edu.vallegrande.sigrc.product_sale.domain.ports.out;

import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductInfo;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IProductClient {
    Mono<ProductInfo> findById(UUID productId);
    Mono<Void> decrementStock(UUID productId, int quantity);
}
