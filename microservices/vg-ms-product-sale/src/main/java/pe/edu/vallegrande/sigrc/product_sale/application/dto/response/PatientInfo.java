package pe.edu.vallegrande.sigrc.product_sale.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientInfo {
    private String patientId;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType;
    private String patientType;
    private String status;
}
