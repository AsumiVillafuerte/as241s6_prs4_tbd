package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "treatments")
public class TreatmentDocument {
    @Id private String id;
    private String code;
    private String name;
    private String specialtyId;
    private BigDecimal salePrice;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
