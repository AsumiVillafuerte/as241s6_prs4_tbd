package pe.edu.vallegrande.sigrc.product.domain.ports.out;

import pe.edu.vallegrande.sigrc.product.domain.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IProductRepository {
    Mono<Product> save(Product product);
    Mono<Product> findById(UUID id);
    Flux<Product> findAll();
    Flux<Product> findByStatus(Character status);
    Mono<Product> update(Product product);
    Mono<Void> deleteById(UUID id);
    Mono<Boolean> existsById(UUID id);
}
