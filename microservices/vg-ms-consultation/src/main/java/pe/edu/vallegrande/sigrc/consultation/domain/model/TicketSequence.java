package pe.edu.vallegrande.sigrc.consultation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TicketSequence {
    private String date;
    private Long sequence;
    private Long version;

    public void increment() {
        this.sequence = this.sequence != null ? this.sequence + 1 : 1L;
    }
}
