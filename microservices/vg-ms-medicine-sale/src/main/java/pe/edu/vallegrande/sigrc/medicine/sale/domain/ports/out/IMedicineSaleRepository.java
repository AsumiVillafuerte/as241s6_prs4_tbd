package pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.out;

import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMedicineSaleRepository {
    Flux<MedicineSale> findAll();
    Flux<MedicineSale> findAllInactive();
    Flux<MedicineSale> findByStatus(String status);
    Mono<MedicineSale> findById(String id);
    Mono<MedicineSale> findByTicket(String ticket);
    Flux<MedicineSale> findByDni(String dni);
    Mono<Boolean> existsByTicket(String ticket);
    Mono<MedicineSale> save(MedicineSale sale);
    Mono<MedicineSale> changeStatus(String id, String status);
}
