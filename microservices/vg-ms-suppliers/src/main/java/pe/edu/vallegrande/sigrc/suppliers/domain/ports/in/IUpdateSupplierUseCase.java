package pe.edu.vallegrande.sigrc.suppliers.domain.ports.in;

import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import reactor.core.publisher.Mono;

public interface IUpdateSupplierUseCase {
    Mono<Supplier> update(Long id, Supplier supplier);
}
