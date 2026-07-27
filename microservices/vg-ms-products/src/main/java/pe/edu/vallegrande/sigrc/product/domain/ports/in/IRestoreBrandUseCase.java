package pe.edu.vallegrande.sigrc.product.domain.ports.in;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IRestoreBrandUseCase {
    Mono<Void> restoreBrand(UUID id);
}
