package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.adapter;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Specialty;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.SpecialtyRepositoryPort;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mongo.SpecialtyMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SpecialtyPersistenceAdapter implements SpecialtyRepositoryPort {

    private final SpecialtyMongoRepository mongoRepository;
    private final PersistenceMapper mapper;

    public SpecialtyPersistenceAdapter(SpecialtyMongoRepository mongoRepository, PersistenceMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<Specialty> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Specialty> findAllPaged(int page, int size) {
        return mongoRepository.findAllBy(PageRequest.of(page, size))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countAll() {
        return mongoRepository.count();
    }

    @Override
    public Mono<Specialty> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Specialty> findByCode(String code) {
        return mongoRepository.findByCode(code)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Specialty> findByStatus(CommonStatus status) {
        return mongoRepository.findByStatus(status.name())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Specialty> save(Specialty specialty) {
        return Mono.just(mapper.toEntity(specialty))
                .flatMap(mongoRepository::save)
                .map(mapper::toDomain);
    }
}
