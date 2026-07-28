package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorClientResponse {
   private String id;
    private String firstName;
    private String lastName;
    private String motherLastName;
    private String status;
    private String specialtyId;
}
