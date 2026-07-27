package pe.edu.vallegrande.sigrc.suppliers.application.usecases;

import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.DuplicateDocumentException;
import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.DuplicateEmailException;
import pe.edu.vallegrande.sigrc.suppliers.domain.models.DocumentType;
import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.ICreateSupplierUseCase;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.out.ISupplierRepository;
import reactor.core.publisher.Mono;

public class CreateSupplierUseCaseImpl implements ICreateSupplierUseCase {

    private final ISupplierRepository supplierRepository;

    public CreateSupplierUseCaseImpl(ISupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Mono<Supplier> create(Supplier supplier) {
        return validateDocumentNumber(supplier.getDocumentNumber())
                .then(validateEmail(supplier.getEmail()))
                .then(validateDocumentLength(supplier))
                .then(saveWithTimestamps(supplier));
    }

    private Mono<Void> validateDocumentNumber(String documentNumber) {
        return supplierRepository.findByDocumentNumber(documentNumber)
                .flatMap(existing -> Mono.<Void>error(new DuplicateDocumentException(documentNumber)))
                .switchIfEmpty(Mono.empty());
    }

    private Mono<Void> validateEmail(String email) {
        if (email == null) return Mono.empty();
        return supplierRepository.findByEmail(email)
                .flatMap(existing -> Mono.<Void>error(new DuplicateEmailException(email)))
                .switchIfEmpty(Mono.empty());
    }

    private Mono<Void> validateDocumentLength(Supplier supplier) {
        DocumentType type = DocumentType.from(supplier.getDocumentType());
        String error = type.validationError(supplier.getDocumentNumber());
        if (error != null) {
            return Mono.error(new DomainException(error));
        }
        return Mono.empty();
    }

    private Mono<Supplier> saveWithTimestamps(Supplier supplier) {
        supplier.initializeForCreation();
        return supplierRepository.save(supplier);
    }
}
