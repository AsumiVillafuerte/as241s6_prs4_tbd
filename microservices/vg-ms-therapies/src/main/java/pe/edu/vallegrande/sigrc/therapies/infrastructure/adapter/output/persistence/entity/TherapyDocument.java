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
@Document(collection = "therapies")
public class TherapyDocument {
    @Id private String id;
    private String ticket;
    private String specialtyId;
    private String clientType;
    private String medic;
    private String medicName;
    private String patient;
    private String patientName;
    private String dni;
    private String registerBy;
    private String registerByName;
    private BigDecimal total;
    private String type;
    private String tarjeta;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
