package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.consultation.domain.model.TicketSequence;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.TicketSequenceRepositoryPort;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.repository.TicketSequenceR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TicketSequencePersistenceAdapter implements TicketSequenceRepositoryPort {

    private final TicketSequenceR2dbcRepository repository;
    private final PersistenceMapper mapper;

    public TicketSequencePersistenceAdapter(TicketSequenceR2dbcRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<TicketSequence> findByDate(String date) {
        return repository.findByDate(date).map(mapper::toDomain);
    }

    @Override
    public Mono<TicketSequence> save(TicketSequence sequence) {
        return repository.save(mapper.toEntity(sequence)).map(mapper::toDomain);
    }
}
