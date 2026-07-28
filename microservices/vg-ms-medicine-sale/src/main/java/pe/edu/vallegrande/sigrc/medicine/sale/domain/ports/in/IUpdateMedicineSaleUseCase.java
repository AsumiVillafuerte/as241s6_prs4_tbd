package pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in;

import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import reactor.core.publisher.Mono;

public interface IUpdateMedicineSaleUseCase {
    Mono<MedicineSale> update(String id, MedicineSale sale);
}
