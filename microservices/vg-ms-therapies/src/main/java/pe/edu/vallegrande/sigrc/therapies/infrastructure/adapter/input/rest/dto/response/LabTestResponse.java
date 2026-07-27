package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class LabTestResponse {
    private String id;
    private String name;
    private BigDecimal price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
