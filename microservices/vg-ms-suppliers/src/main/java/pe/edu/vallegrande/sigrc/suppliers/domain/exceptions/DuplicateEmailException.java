package pe.edu.vallegrande.sigrc.suppliers.domain.exceptions;

public class DuplicateEmailException extends DomainException {

    public DuplicateEmailException(String email) {
        super("Ya existe un proveedor con el email " + email);
    }
}
