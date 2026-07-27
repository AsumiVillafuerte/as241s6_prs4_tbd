package pe.edu.vallegrande.sigrc.product.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product.domain.models.Product;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IProductRepository;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.mappers.ProductPersistenceMapper;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.repositories.ProductR2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements IProductRepository {

    private final ProductR2dbcRepository r2dbcRepository;
    private final ProductPersistenceMapper mapper;

    @Override
    public Mono<Product> save(Product product) {
        return r2dbcRepository.save(mapper.toEntity(product))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Product> findById(UUID id) {
        return r2dbcRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findAll() {
        return r2dbcRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findByStatus(Character status) {
        return r2dbcRepository.findByStatus(String.valueOf(status))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Product> update(Product product) {
        return r2dbcRepository.save(mapper.toEntity(product))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return r2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return r2dbcRepository.existsById(id);
    }
}
