package pe.edu.vallegrande.sigrc.consultation.application.service;

import pe.edu.vallegrande.sigrc.consultation.domain.model.TicketSequence;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.TicketSequenceUseCase;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.TicketSequenceRepositoryPort;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TicketApplicationService implements TicketSequenceUseCase {

    private static final String PREFIX = "CONS";
    private final TicketSequenceRepositoryPort repositoryPort;

    public TicketApplicationService(TicketSequenceRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Mono<String> generateTicket() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        return repositoryPort.findByDate(today)
                .flatMap(seq -> {
                    seq.increment();
                    return repositoryPort.save(seq);
                })
                .switchIfEmpty(Mono.defer(() ->
                        repositoryPort.save(new TicketSequence(today, 1L, null))
                ))
                .map(seq -> String.format("%s-%s-%06d", PREFIX, today, seq.getSequence()));
    }
}
