package pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in;

import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGetMedicineSaleUseCase {
    Flux<MedicineSale> findAll();
    Flux<MedicineSale> findAllInactive();
    Flux<MedicineSale> findByStatus(String status);
    Mono<MedicineSale> findById(String id);
    Mono<MedicineSale> findByTicket(String ticket);
    Flux<MedicineSale> findByDni(String dni);
}
