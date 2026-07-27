package pe.edu.vallegrande.sigrc.product.domain.ports.in;

import pe.edu.vallegrande.sigrc.product.application.dto.request.CreateProductRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.ProductResponse;
import reactor.core.publisher.Mono;

public interface ICreateProductUseCase {
    Mono<ProductResponse> createProduct(CreateProductRequest request);
}
