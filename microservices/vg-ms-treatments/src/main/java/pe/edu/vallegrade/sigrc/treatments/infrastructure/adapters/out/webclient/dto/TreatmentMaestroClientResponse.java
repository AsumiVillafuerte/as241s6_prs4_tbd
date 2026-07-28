package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentMaestroClientResponse {
    private String id;
    private String code;
    private String name;
    private String specialtyId;
    private BigDecimal salePrice;
    private String status;
}
