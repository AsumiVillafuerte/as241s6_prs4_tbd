package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientClientResponse {
    private String patientId;
    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private String status;
}
