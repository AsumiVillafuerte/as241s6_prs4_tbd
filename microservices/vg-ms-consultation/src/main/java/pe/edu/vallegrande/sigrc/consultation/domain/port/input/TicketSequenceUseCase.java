package pe.edu.vallegrande.sigrc.consultation.domain.port.input;

import reactor.core.publisher.Mono;

public interface TicketSequenceUseCase {
    Mono<String> generateTicket();
}
