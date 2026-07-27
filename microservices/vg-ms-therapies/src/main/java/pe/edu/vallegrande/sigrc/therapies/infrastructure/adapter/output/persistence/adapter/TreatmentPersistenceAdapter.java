package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.therapies.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TreatmentRepository;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository.TreatmentMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TreatmentPersistenceAdapter implements TreatmentRepository {

    private final TreatmentMongoRepository repository;
    private final PersistenceMapper mapper;

    public TreatmentPersistenceAdapter(TreatmentMongoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override public Flux<Treatment> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override public Flux<Treatment> findAllPaged(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size).map(mapper::toDomain);
    }

    @Override public Mono<Long> countAll() {
        return repository.findAll().count();
    }

    @Override public Mono<Treatment> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override public Flux<Treatment> findBySpecialtyId(String specialtyId) {
        return repository.findBySpecialtyId(specialtyId).map(mapper::toDomain);
    }

    @Override public Mono<Treatment> save(Treatment treatment) {
        return repository.save(mapper.toDocument(treatment)).map(mapper::toDomain);
    }

    @Override public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
