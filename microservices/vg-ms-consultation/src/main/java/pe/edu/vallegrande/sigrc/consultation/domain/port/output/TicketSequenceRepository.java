package pe.edu.vallegrande.sigrc.consultation.domain.port.output;

import pe.edu.vallegrande.sigrc.consultation.domain.model.TicketSequence;
import reactor.core.publisher.Mono;

public interface TicketSequenceRepository {
    Mono<TicketSequence> findByDate(String date);
    Mono<TicketSequence> save(TicketSequence sequence);
}
