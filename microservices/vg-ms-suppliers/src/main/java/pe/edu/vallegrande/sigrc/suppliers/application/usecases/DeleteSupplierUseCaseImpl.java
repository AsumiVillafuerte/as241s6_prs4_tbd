package pe.edu.vallegrande.sigrc.suppliers.application.usecases;

import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.IDeleteSupplierUseCase;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.out.ISupplierRepository;
import reactor.core.publisher.Mono;

public class DeleteSupplierUseCaseImpl implements IDeleteSupplierUseCase {

    private final ISupplierRepository supplierRepository;

    public DeleteSupplierUseCaseImpl(ISupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Mono<Void> deactivate(Long id) {
        return supplierRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Proveedor no encontrado con id " + id)))
                .flatMap(supplier -> {
                    supplier.deactivate();
                    return supplierRepository.save(supplier);
                })
                .then();
    }

    @Override
    public Mono<Void> restore(Long id) {
        return supplierRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Proveedor no encontrado con id " + id)))
                .flatMap(supplier -> {
                    supplier.restore();
                    return supplierRepository.save(supplier);
                })
                .then();
    }
}
