package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.adapters.out.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicineSaleReactiveRepository extends ReactiveMongoRepository<MedicineSaleDocument, String> {
    Mono<MedicineSaleDocument> findByTicket(String ticket);
    Flux<MedicineSaleDocument> findByStatus(String status);
    Flux<MedicineSaleDocument> findByStatusNot(String status);
    Flux<MedicineSaleDocument> findByDni(String dni);
    Mono<Boolean> existsByTicket(String ticket);
}
