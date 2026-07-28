package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.adapter;

import pe.edu.vallegrande.sigrc.consultation.domain.model.Consultation;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.ConsultationRepositoryPort;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.repository.ConsultationR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ConsultationPersistenceAdapter implements ConsultationRepositoryPort {

    private final ConsultationR2dbcRepository repository;
    private final PersistenceMapper mapper;

    public ConsultationPersistenceAdapter(ConsultationR2dbcRepository repository, PersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<Consultation> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<Consultation> findAllPaged(int page, int size) {
        return repository.findAll().skip((long) page * size).take(size)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countAll() {
        return repository.findAll().count();
    }

    @Override
    public Mono<Consultation> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Consultation> findByTicket(String ticket) {
        return repository.findByTicket(ticket).map(mapper::toDomain);
    }

    @Override
    public Mono<Consultation> save(Consultation consultation) {
        return repository.save(mapper.toEntity(consultation)).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
