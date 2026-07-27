package pe.edu.vallegrande.sigrc.shopping.domain.exception;

public class DuplicateComprobanteException extends DomainException {

    public DuplicateComprobanteException(String numeroComprobante) {
        super("El número de comprobante '" + numeroComprobante + "' ya se encuentra registrado");
    }
}
