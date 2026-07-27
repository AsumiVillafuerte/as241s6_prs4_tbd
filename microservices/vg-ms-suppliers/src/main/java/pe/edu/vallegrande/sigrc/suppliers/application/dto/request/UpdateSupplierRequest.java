package pe.edu.vallegrande.sigrc.suppliers.application.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateSupplierRequest {

    private String businessName;
    private String address;
    private String phone;

    @Email(message = "El correo electrónico no tiene un formato válido")
    private String email;

    private String imageUrl;

    private String documentType;

    private String documentNumber;
}
