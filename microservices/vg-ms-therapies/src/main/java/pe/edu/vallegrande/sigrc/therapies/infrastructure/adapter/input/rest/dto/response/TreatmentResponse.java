package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TreatmentResponse {
    private String id;
    private String code;
    private String name;
    private String specialtyId;
    private BigDecimal salePrice;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
