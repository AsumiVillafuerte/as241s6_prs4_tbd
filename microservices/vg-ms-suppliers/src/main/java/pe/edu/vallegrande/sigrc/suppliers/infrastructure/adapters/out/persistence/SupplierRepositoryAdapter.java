package pe.edu.vallegrande.sigrc.suppliers.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.out.ISupplierRepository;
import pe.edu.vallegrande.sigrc.suppliers.infrastructure.persistence.mappers.SupplierPersistenceMapper;
import pe.edu.vallegrande.sigrc.suppliers.infrastructure.persistence.repositories.SupplierR2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SupplierRepositoryAdapter implements ISupplierRepository {

    private final SupplierR2dbcRepository r2dbcRepository;

    public SupplierRepositoryAdapter(SupplierR2dbcRepository r2dbcRepository) {
        this.r2dbcRepository = r2dbcRepository;
    }

    @Override
    public Mono<Supplier> save(Supplier supplier) {
        return r2dbcRepository.save(SupplierPersistenceMapper.toEntity(supplier))
                .map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Supplier> findById(Long id) {
        return r2dbcRepository.findById(id)
                .map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Supplier> findByDocumentNumber(String documentNumber) {
        return r2dbcRepository.findByDocumentNumber(documentNumber)
                .map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Supplier> findByEmail(String email) {
        return r2dbcRepository.findByEmail(email)
                .map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Supplier> findAll() {
        return r2dbcRepository.findAll()
                .map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Supplier> findByStatus(Boolean status) {
        return r2dbcRepository.findByStatus(status)
                .map(SupplierPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Supplier> findByDocumentType(String documentType) {
        return r2dbcRepository.findByDocumentType(documentType)
                .map(SupplierPersistenceMapper::toDomain);
    }
}
