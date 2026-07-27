package pe.edu.vallegrande.sigrc.suppliers.domain.ports.in;

import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGetSupplierUseCase {
    Flux<Supplier> findAll();
    Mono<Supplier> findById(Long id);
    Flux<Supplier> findByStatus(Boolean status);
    Flux<Supplier> findByDocumentType(String documentType);
}
