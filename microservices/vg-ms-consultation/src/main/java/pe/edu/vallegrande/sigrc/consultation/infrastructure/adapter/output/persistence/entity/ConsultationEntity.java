package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("consultations")
public class ConsultationEntity {
    @Id private String id;
    @Version private Long version;
    private String ticket;
    private String specialtyId;
    private String medic;
    private String medicName;
    private String patient;
    private String patientName;
    private String dni;
    private String registerBy;
    private String registerByName;
    private BigDecimal total;
    private String tipo;
    private String tarjeta;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
