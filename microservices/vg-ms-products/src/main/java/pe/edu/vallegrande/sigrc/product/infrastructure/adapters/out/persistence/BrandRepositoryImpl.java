package pe.edu.vallegrande.sigrc.product.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product.domain.models.Brand;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IBrandRepository;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.mappers.BrandPersistenceMapper;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.repositories.BrandR2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BrandRepositoryImpl implements IBrandRepository {

    private final BrandR2dbcRepository r2dbcRepository;
    private final BrandPersistenceMapper mapper;

    @Override
    public Mono<Brand> save(Brand brand) {
        return r2dbcRepository.save(mapper.toEntity(brand))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Brand> findById(UUID id) {
        return r2dbcRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Brand> findAll() {
        return r2dbcRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Brand> findByStatus(Character status) {
        return r2dbcRepository.findByStatus(String.valueOf(status))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Brand> update(Brand brand) {
        return r2dbcRepository.save(mapper.toEntity(brand))
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
