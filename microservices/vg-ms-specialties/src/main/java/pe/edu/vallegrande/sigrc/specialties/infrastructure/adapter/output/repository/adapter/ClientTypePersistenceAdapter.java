package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.adapter;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.specialties.domain.model.ClientType;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.ClientTypeRepositoryPort;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mongo.ClientTypeMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ClientTypePersistenceAdapter implements ClientTypeRepositoryPort {

    private final ClientTypeMongoRepository mongoRepository;
    private final PersistenceMapper mapper;

    public ClientTypePersistenceAdapter(ClientTypeMongoRepository mongoRepository, PersistenceMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<ClientType> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ClientType> findAllPaged(int page, int size) {
        return mongoRepository.findAllBy(PageRequest.of(page, size))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countAll() {
        return mongoRepository.count();
    }

    @Override
    public Mono<ClientType> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ClientType> findByStatus(CommonStatus status) {
        return mongoRepository.findByStatus(status.name())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ClientType> save(ClientType clientType) {
        return Mono.just(mapper.toEntity(clientType))
                .flatMap(mongoRepository::save)
                .map(mapper::toDomain);
    }
}
