package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyClientResponse {
    private String id;
    private String name;
    private String category;
    private String status;
}
