package pe.edu.vallegrande.sigrc.medications.domain.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
