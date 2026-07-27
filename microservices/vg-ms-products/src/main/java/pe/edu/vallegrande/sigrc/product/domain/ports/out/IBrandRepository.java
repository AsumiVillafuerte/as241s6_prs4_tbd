package pe.edu.vallegrande.sigrc.product.domain.ports.out;

import pe.edu.vallegrande.sigrc.product.domain.models.Brand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IBrandRepository {
    Mono<Brand> save(Brand brand);
    Mono<Brand> findById(UUID id);
    Flux<Brand> findAll();
    Flux<Brand> findByStatus(Character status);
    Mono<Brand> update(Brand brand);
    Mono<Void> deleteById(UUID id);
    Mono<Boolean> existsById(UUID id);
}
