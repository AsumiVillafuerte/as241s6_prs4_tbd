package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.therapies.domain.model.LabTest;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.LabTestRepository;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository.LabTestMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LabTestPersistenceAdapter implements LabTestRepository {

    private final LabTestMongoRepository repository;
    private final PersistenceMapper mapper;

    public LabTestPersistenceAdapter(LabTestMongoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override public Flux<LabTest> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override public Flux<LabTest> findAllPaged(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size).map(mapper::toDomain);
    }

    @Override public Mono<Long> countAll() {
        return repository.findAll().count();
    }

    @Override public Mono<LabTest> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override public Mono<LabTest> save(LabTest labTest) {
        return repository.save(mapper.toDocument(labTest)).map(mapper::toDomain);
    }

    @Override public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
