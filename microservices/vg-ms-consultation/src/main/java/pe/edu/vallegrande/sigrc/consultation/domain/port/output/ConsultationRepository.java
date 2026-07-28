package pe.edu.vallegrande.sigrc.consultation.domain.port.output;

import pe.edu.vallegrande.sigrc.consultation.domain.model.Consultation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConsultationRepository {
    Flux<Consultation> findAll();
    Flux<Consultation> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<Consultation> findById(String id);
    Mono<Consultation> findByTicket(String ticket);
    Mono<Consultation> save(Consultation consultation);
    Mono<Void> deleteById(String id);
}
