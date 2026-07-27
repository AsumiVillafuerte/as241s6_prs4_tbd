package pe.edu.vallegrande.sigrc.specialties.domain.exception;

public class DomainException extends RuntimeException {
    
    public DomainException(String message) {
        super(message);
    }

    /**
     * Optimización Reactiva: Sobrescribir este método evita que la JVM gaste recursos
     * llenando el stack trace para errores de flujo esperados por el negocio.
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
