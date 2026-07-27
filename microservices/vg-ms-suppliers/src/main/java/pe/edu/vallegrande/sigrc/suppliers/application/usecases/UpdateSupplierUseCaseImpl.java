package pe.edu.vallegrande.sigrc.suppliers.application.usecases;

import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.DuplicateDocumentException;
import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.DuplicateEmailException;
import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.suppliers.domain.models.DocumentType;
import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.IUpdateSupplierUseCase;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.out.ISupplierRepository;
import reactor.core.publisher.Mono;

public class UpdateSupplierUseCaseImpl implements IUpdateSupplierUseCase {

    private final ISupplierRepository supplierRepository;

    public UpdateSupplierUseCaseImpl(ISupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Mono<Supplier> update(Long id, Supplier updated) {
        return supplierRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Proveedor no encontrado con id " + id)))
                .flatMap(existing -> applyChanges(existing, updated))
                .flatMap(supplierRepository::save);
    }

    private Mono<Supplier> applyChanges(Supplier existing, Supplier updated) {
        if (updated.getBusinessName() != null) existing.setBusinessName(updated.getBusinessName());
        if (updated.getAddress() != null)      existing.setAddress(updated.getAddress());
        if (updated.getPhone() != null)        existing.setPhone(updated.getPhone());
        if (updated.getImageUrl() != null)     existing.setImageUrl(updated.getImageUrl());

        // documentType se aplica primero para que la validación cruzada use el tipo efectivo
        if (updated.getDocumentType() != null) existing.setDocumentType(updated.getDocumentType());

        return validateEmail(existing, updated)
                .then(validateDocumentNumber(existing, updated))
                .then(Mono.fromCallable(() -> {
                    existing.markAsUpdated();
                    return existing;
                }));
    }

    private Mono<Void> validateEmail(Supplier existing, Supplier updated) {
        if (updated.getEmail() == null || updated.getEmail().equals(existing.getEmail())) {
            return Mono.empty();
        }
        return supplierRepository.findByEmail(updated.getEmail())
                .flatMap(found -> Mono.<Void>error(new DuplicateEmailException(updated.getEmail())))
                .switchIfEmpty(Mono.fromRunnable(() -> existing.setEmail(updated.getEmail())));
    }

    private Mono<Void> validateDocumentNumber(Supplier existing, Supplier updated) {
        if (updated.getDocumentNumber() == null || updated.getDocumentNumber().equals(existing.getDocumentNumber())) {
            return Mono.empty();
        }
        DocumentType type = DocumentType.from(existing.getDocumentType());
        String error = type.validationError(updated.getDocumentNumber());
        if (error != null) {
            return Mono.error(new DomainException(error));
        }
        return supplierRepository.findByDocumentNumber(updated.getDocumentNumber())
                .flatMap(found -> Mono.<Void>error(new DuplicateDocumentException(updated.getDocumentNumber())))
                .switchIfEmpty(Mono.fromRunnable(() -> existing.setDocumentNumber(updated.getDocumentNumber())));
    }

}
