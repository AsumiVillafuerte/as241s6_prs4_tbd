package pe.edu.vallegrande.sigrc.product_sale.domain.exceptions;

public class PatientNotEligibleException extends DomainException {
    public PatientNotEligibleException(String message) {
        super(message);
    }
}
