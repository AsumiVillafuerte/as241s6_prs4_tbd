package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.therapies.domain.model.LabKit;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.LabKitRepository;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository.LabKitMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LabKitPersistenceAdapter implements LabKitRepository {

    private final LabKitMongoRepository repository;
    private final PersistenceMapper mapper;

    public LabKitPersistenceAdapter(LabKitMongoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override public Flux<LabKit> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override public Flux<LabKit> findAllPaged(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size).map(mapper::toDomain);
    }

    @Override public Mono<Long> countAll() {
        return repository.findAll().count();
    }

    @Override public Mono<LabKit> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override public Mono<LabKit> save(LabKit labKit) {
        return repository.save(mapper.toDocument(labKit)).map(mapper::toDomain);
    }

    @Override public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
