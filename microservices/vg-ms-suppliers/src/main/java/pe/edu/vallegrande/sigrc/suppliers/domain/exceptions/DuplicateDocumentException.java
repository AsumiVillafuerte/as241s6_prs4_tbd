package pe.edu.vallegrande.sigrc.suppliers.domain.exceptions;

public class DuplicateDocumentException extends DomainException {

    public DuplicateDocumentException(String documentNumber) {
        super("Ya existe un proveedor con el documento " + documentNumber);
    }
}
