package pe.edu.vallegrande.sigrc.suppliers.domain.ports.in;

import reactor.core.publisher.Mono;

public interface IDeleteSupplierUseCase {
    Mono<Void> deactivate(Long id);
    Mono<Void> restore(Long id);
}
