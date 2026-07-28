package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("ticket_sequences")
public class TicketSequenceEntity {
    @Id private String date;
    @Version private Long version;
    private Long sequence;
}
