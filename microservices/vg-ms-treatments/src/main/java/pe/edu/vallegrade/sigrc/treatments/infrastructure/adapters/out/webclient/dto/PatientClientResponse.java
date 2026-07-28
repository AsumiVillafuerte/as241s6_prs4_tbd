package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientClientResponse {
    private String patientId;
    private String firstName;
    private String lastName;
    private String documentType; 
    private String documentNumber;
    private String status;
}
