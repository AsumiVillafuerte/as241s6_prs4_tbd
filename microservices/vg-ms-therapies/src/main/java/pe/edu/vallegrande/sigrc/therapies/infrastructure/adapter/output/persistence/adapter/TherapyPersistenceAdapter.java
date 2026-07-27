package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.therapies.domain.model.Therapy;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TherapyRepository;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository.TherapyMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TherapyPersistenceAdapter implements TherapyRepository {

    private final TherapyMongoRepository repository;
    private final PersistenceMapper mapper;

    public TherapyPersistenceAdapter(TherapyMongoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override public Flux<Therapy> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override public Flux<Therapy> findAllPaged(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size).map(mapper::toDomain);
    }

    @Override public Mono<Long> countAll() {
        return repository.findAll().count();
    }

    @Override public Mono<Therapy> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override public Mono<Therapy> findByTicket(String ticket) {
        return repository.findByTicket(ticket).map(mapper::toDomain);
    }

    @Override public Mono<Therapy> save(Therapy therapy) {
        return repository.save(mapper.toDocument(therapy)).map(mapper::toDomain);
    }

    @Override public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
