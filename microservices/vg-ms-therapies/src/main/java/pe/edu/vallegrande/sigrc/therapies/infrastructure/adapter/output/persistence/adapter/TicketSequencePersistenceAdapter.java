package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.therapies.domain.model.TicketSequence;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TicketSequenceRepository;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository.TicketSequenceMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TicketSequencePersistenceAdapter implements TicketSequenceRepository {

    private final TicketSequenceMongoRepository repository;
    private final PersistenceMapper mapper;

    public TicketSequencePersistenceAdapter(TicketSequenceMongoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<TicketSequence> findByDate(String date) {
        return repository.findByDate(date).map(mapper::toDomain);
    }

    @Override
    public Mono<TicketSequence> save(TicketSequence sequence) {
        return repository.save(mapper.toDocument(sequence)).map(mapper::toDomain);
    }
}
