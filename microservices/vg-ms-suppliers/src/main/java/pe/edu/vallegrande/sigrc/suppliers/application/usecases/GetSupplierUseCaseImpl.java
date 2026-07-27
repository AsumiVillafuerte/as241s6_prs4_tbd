package pe.edu.vallegrande.sigrc.suppliers.application.usecases;

import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.IGetSupplierUseCase;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.out.ISupplierRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetSupplierUseCaseImpl implements IGetSupplierUseCase {

    private final ISupplierRepository supplierRepository;

    public GetSupplierUseCaseImpl(ISupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Flux<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Mono<Supplier> findById(Long id) {
        return supplierRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Proveedor no encontrado con id " + id)));
    }

    @Override
    public Flux<Supplier> findByStatus(Boolean status) {
        return supplierRepository.findByStatus(status);
    }

    @Override
    public Flux<Supplier> findByDocumentType(String documentType) {
        return supplierRepository.findByDocumentType(documentType);
    }
}
