package pe.edu.vallegrande.sigrc.therapies.domain.port.output;

import pe.edu.vallegrande.sigrc.therapies.domain.model.TicketSequence;
import reactor.core.publisher.Mono;

public interface TicketSequenceRepository {
    Mono<TicketSequence> findByDate(String date);
    Mono<TicketSequence> save(TicketSequence sequence);
}
