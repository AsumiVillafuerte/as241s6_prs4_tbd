package pe.edu.vallegrande.sigrc.shopping.domain.exception;

public class ExternalServiceException extends DomainException {

    public ExternalServiceException(String servicio, String message) {
        super("Error al comunicarse con " + servicio + ": " + message);
    }

    public ExternalServiceException(String servicio, Throwable cause) {
        super("Error al comunicarse con " + servicio + ": " + cause.getMessage(), cause);
    }
}
