package pe.edu.vallegrande.sigrc.suppliers.domain.ports.out;

import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISupplierRepository {
    Mono<Supplier> save(Supplier supplier);
    Mono<Supplier> findById(Long id);
    Mono<Supplier> findByDocumentNumber(String documentNumber);
    Mono<Supplier> findByEmail(String email);
    Flux<Supplier> findAll();
    Flux<Supplier> findByStatus(Boolean status);
    Flux<Supplier> findByDocumentType(String documentType);
}
