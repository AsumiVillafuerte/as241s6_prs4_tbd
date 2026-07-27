package pe.edu.vallegrande.sigrc.product.domain.ports.in;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IDeleteProductUseCase {
    Mono<Void> deleteProduct(UUID id);
    Mono<Void> logicalDeleteProduct(UUID id);
}

