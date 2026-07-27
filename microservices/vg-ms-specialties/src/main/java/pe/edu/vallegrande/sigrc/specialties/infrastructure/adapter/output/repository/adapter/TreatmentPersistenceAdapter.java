package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.adapter;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.TreatmentRepositoryPort;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mongo.TreatmentMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TreatmentPersistenceAdapter implements TreatmentRepositoryPort {

    private final TreatmentMongoRepository mongoRepository;
    private final PersistenceMapper mapper;

    public TreatmentPersistenceAdapter(TreatmentMongoRepository mongoRepository, PersistenceMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<Treatment> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Treatment> findAllPaged(int page, int size) {
        return mongoRepository.findAllBy(PageRequest.of(page, size))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countAll() {
        return mongoRepository.count();
    }

    @Override
    public Mono<Treatment> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Treatment> findByCode(String code) {
        return mongoRepository.findByCode(code)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Treatment> findBySpecialtyId(String specialtyId) {
        return mongoRepository.findBySpecialtyId(specialtyId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Treatment> findByStatus(CommonStatus status) {
        return mongoRepository.findByStatus(status.name())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Treatment> save(Treatment treatment) {
        return Mono.just(mapper.toEntity(treatment))
                .flatMap(mongoRepository::save)
                .map(mapper::toDomain);
    }
}
