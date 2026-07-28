package pe.edu.vallegrande.sigrc.medications.domain.exceptions;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
