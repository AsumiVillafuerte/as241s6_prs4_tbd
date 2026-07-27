package pe.edu.vallegrande.sigrc.therapies.application.service;

import pe.edu.vallegrande.sigrc.therapies.domain.model.TicketSequence;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TicketSequenceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TicketService {

    private static final String PREFIX = "TRAT";
    private final TicketSequenceRepository repository;

    public TicketService(TicketSequenceRepository repository) {
        this.repository = repository;
    }

    public Mono<String> generateTicket() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        return repository.findByDate(today)
            .flatMap(seq -> {
                seq.setSequence(seq.getSequence() + 1);
                return repository.save(seq);
            })
            .switchIfEmpty(Mono.defer(() ->
                repository.save(new TicketSequence(today, 1L))
            ))
            .map(seq -> String.format("%s-%s-%06d", PREFIX, today, seq.getSequence()));
    }
}
