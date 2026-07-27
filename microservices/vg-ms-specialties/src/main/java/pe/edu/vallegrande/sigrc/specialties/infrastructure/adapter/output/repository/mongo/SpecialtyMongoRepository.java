package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.entity.SpecialtyDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpecialtyMongoRepository extends ReactiveMongoRepository<SpecialtyDocument, String> {
    Flux<SpecialtyDocument> findByStatus(String status);
    Flux<SpecialtyDocument> findAllBy(Pageable pageable);
    Mono<SpecialtyDocument> findByCode(String code);
}
