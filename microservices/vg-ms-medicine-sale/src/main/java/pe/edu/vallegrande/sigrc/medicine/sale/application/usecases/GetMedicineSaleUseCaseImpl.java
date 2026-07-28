package pe.edu.vallegrande.sigrc.medicine.sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.IGetMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.out.IMedicineSaleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetMedicineSaleUseCaseImpl implements IGetMedicineSaleUseCase {

    private final IMedicineSaleRepository repository;

    @Override
    public Flux<MedicineSale> findAll() {
        log.debug("Buscando todas las ventas activas");
        return repository.findAll();
    }

    @Override
    public Flux<MedicineSale> findAllInactive() {
        log.debug("Buscando todas las ventas inactivas");
        return repository.findAllInactive();
    }

    @Override
    public Flux<MedicineSale> findByStatus(String status) {
        log.debug("Buscando ventas por status: {}", status);
        return repository.findByStatus(status.toUpperCase());
    }

    @Override
    public Mono<MedicineSale> findById(String id) {
        log.debug("Buscando venta por id: {}", id);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException.forId(id)));
    }

    @Override
    public Mono<MedicineSale> findByTicket(String ticket) {
        log.debug("Buscando venta por ticket: {}", ticket);
        return repository.findByTicket(ticket)
                .switchIfEmpty(Mono.error(NotFoundException.forTicket(ticket)));
    }

    @Override
    public Flux<MedicineSale> findByDni(String dni) {
        log.debug("Buscando ventas por DNI: {}", dni);
        return repository.findByDni(dni);
    }
}
