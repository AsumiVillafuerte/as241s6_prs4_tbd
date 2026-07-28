package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.consultation.domain.model.ConsultationPrice;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.ConsultationPriceRepositoryPort;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.repository.ConsultationPriceR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ConsultationPricePersistenceAdapter implements ConsultationPriceRepositoryPort {

    private final ConsultationPriceR2dbcRepository repository;
    private final PersistenceMapper mapper;

    public ConsultationPricePersistenceAdapter(ConsultationPriceR2dbcRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<ConsultationPrice> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<ConsultationPrice> findAllPaged(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countAll() {
        return repository.findAll().count();
    }

    @Override
    public Mono<ConsultationPrice> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Flux<ConsultationPrice> findBySpecialtyId(String specialtyId) {
        return repository.findBySpecialtyId(specialtyId).map(mapper::toDomain);
    }

    @Override
    public Mono<ConsultationPrice> save(ConsultationPrice consultationPrice) {
        return repository.save(mapper.toEntity(consultationPrice)).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
