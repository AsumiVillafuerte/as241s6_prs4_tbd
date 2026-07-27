package pe.edu.vallegrande.sigrc.product.domain.ports.in;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IDeleteBrandUseCase {
    Mono<Void> deleteBrand(UUID id);
    Mono<Void> logicalDeleteBrand(UUID id);
}
