package pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in;

import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import reactor.core.publisher.Mono;

public interface IDeleteMedicineSaleUseCase {
    Mono<MedicineSale> revoke(String id);
    Mono<MedicineSale> delete(String id);
    Mono<MedicineSale> restore(String id);
    Mono<MedicineSale> markAsPaid(String id);
}
