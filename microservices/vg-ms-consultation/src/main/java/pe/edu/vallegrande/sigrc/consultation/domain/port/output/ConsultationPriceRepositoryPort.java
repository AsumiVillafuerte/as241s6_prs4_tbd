package pe.edu.vallegrande.sigrc.consultation.domain.port.output;

import pe.edu.vallegrande.sigrc.consultation.domain.model.ConsultationPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConsultationPriceRepositoryPort {
    Flux<ConsultationPrice> findAll();
    Flux<ConsultationPrice> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<ConsultationPrice> findById(String id);
    Flux<ConsultationPrice> findBySpecialtyId(String specialtyId);
    Mono<ConsultationPrice> save(ConsultationPrice consultationPrice);
    Mono<Void> deleteById(String id);
}
