package pe.edu.vallegrande.sigrc.product_sale.domain.exceptions;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
