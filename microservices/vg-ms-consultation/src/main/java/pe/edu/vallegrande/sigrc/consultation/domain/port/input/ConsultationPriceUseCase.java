package pe.edu.vallegrande.sigrc.consultation.domain.port.input;

import pe.edu.vallegrande.sigrc.consultation.domain.model.ConsultationPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConsultationPriceUseCase {
    Flux<ConsultationPrice> getAll();
    Flux<ConsultationPrice> getPaged(int page, int size);
    Mono<Long> count();
    Mono<ConsultationPrice> getById(String id);
    Flux<ConsultationPrice> getBySpecialty(String specialtyId);
    Mono<ConsultationPrice> register(ConsultationPrice consultationPrice);
    Mono<ConsultationPrice> modify(String id, ConsultationPrice consultationPrice);
    Mono<ConsultationPrice> activate(String id);
    Mono<ConsultationPrice> deactivate(String id);
}
