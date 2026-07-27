package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.therapies.domain.model.TherapyPrice;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TherapyPriceRepository;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository.TherapyPriceMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TherapyPricePersistenceAdapter implements TherapyPriceRepository {

    private final TherapyPriceMongoRepository repository;
    private final PersistenceMapper mapper;

    public TherapyPricePersistenceAdapter(TherapyPriceMongoRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override public Flux<TherapyPrice> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override public Flux<TherapyPrice> findAllPaged(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size).map(mapper::toDomain);
    }

    @Override public Mono<Long> countAll() {
        return repository.findAll().count();
    }

    @Override public Mono<TherapyPrice> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override public Flux<TherapyPrice> findBySpecialtyId(String specialtyId) {
        return repository.findBySpecialtyId(specialtyId).map(mapper::toDomain);
    }

    @Override public Mono<TherapyPrice> save(TherapyPrice therapyPrice) {
        return repository.save(mapper.toDocument(therapyPrice)).map(mapper::toDomain);
    }

    @Override public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
