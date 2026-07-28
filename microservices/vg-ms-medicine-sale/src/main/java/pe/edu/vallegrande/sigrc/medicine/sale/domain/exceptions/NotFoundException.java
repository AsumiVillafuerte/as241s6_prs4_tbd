package pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions;

public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException forId(String id) {
        return new NotFoundException("Venta no encontrada con id: " + id);
    }

    public static NotFoundException forTicket(String ticket) {
        return new NotFoundException("Venta no encontrada con ticket: " + ticket);
    }

    public static NotFoundException forDni(String dni) {
        return new NotFoundException("No se encontraron ventas para el DNI: " + dni);
    }
}
