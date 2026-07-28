package pe.edu.vallegrande.sigrc.consultation.domain.port.input;

import pe.edu.vallegrande.sigrc.consultation.domain.model.Consultation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConsultationUseCase {
    Flux<Consultation> getAll();
    Flux<Consultation> getPaged(int page, int size);
    Mono<Long> count();
    Mono<Consultation> getById(String id);
    Mono<Consultation> register(Consultation consultation);
    Mono<Consultation> modify(String id, Consultation consultation);
    Mono<Consultation> cancel(String id);
    Mono<Consultation> restore(String id);
}
