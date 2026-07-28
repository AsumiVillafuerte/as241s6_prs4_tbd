package pe.edu.vallegrande.sigrc.medicine.sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.SaleItem;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.IDeleteMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.out.IMedicineSaleRepository;
import pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients.MedicationClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteMedicineSaleUseCaseImpl implements IDeleteMedicineSaleUseCase {

    private final IMedicineSaleRepository repository;
    private final MedicationClient medicationClient;

    @Override
    public Mono<MedicineSale> revoke(String id) {
        log.debug("Revocando venta con id: {}", id);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException.forId(id)))
                .flatMap(sale -> {
                    if (SaleStatus.REVOCADO.equals(sale.getStatus())) {
                        return Mono.error(new DomainException("La venta ya está revocada"));
                    }
                    if (SaleStatus.INACTIVE.equals(sale.getStatus())) {
                        return Mono.error(new DomainException("No se puede revocar una venta eliminada"));
                    }
                    // Cambiar estado y devolver stock
                    return repository.changeStatus(id, SaleStatus.REVOCADO.name())
                            .flatMap(revokedSale ->
                                    restoreStockForAll(revokedSale.getItems())
                                            .thenReturn(revokedSale)
                            );
                });
    }

    @Override
    public Mono<MedicineSale> delete(String id) {
        log.debug("Eliminando lógicamente venta con id: {}", id);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException.forId(id)))
                .flatMap(sale -> {
                    if (SaleStatus.INACTIVE.equals(sale.getStatus())) {
                        return Mono.error(new DomainException("La venta ya está eliminada"));
                    }
                    return repository.changeStatus(id, SaleStatus.INACTIVE.name());
                });
    }

    @Override
    public Mono<MedicineSale> restore(String id) {
        log.debug("Restaurando venta con id: {}", id);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException.forId(id)))
                .flatMap(sale -> {
                    if (!SaleStatus.INACTIVE.equals(sale.getStatus())
                            && !SaleStatus.REVOCADO.equals(sale.getStatus())) {
                        return Mono.error(new DomainException(
                                "Solo se puede restaurar una venta REVOCADA o ELIMINADA"));
                    }

                    boolean wasRevoked = SaleStatus.REVOCADO.equals(sale.getStatus());

                    if (wasRevoked) {
                        return validateStock(sale.getItems())
                                .then(repository.changeStatus(id, SaleStatus.CONSIGNADO.name()))
                                .flatMap(restoredSale ->
                                        decreaseStockForAll(restoredSale.getItems())
                                                .thenReturn(restoredSale)
                                );
                    }

                    // Desde INACTIVE solo cambia estado, sin tocar stock
                    return repository.changeStatus(id, SaleStatus.CONSIGNADO.name());
                });
    }

    @Override
    public Mono<MedicineSale> markAsPaid(String id) {
        log.debug("Marcando venta como donación con id: {}", id);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException.forId(id)))
                .flatMap(sale -> {
                    if (!SaleStatus.CONSIGNADO.equals(sale.getStatus())) {
                        return Mono.error(new DomainException(
                                "Solo se puede marcar como donación una venta CONSIGNADA"));
                    }
                    // Cambia el tipo a DONADO manteniendo el estado CONSIGNADO
                    sale.setType(pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleType.DONADO);
                    return repository.save(sale);
                });
    }

    /**
     * Devuelve el stock a ms-medications cuando se revoca una venta.
     */
    private Mono<Void> restoreStockForAll(List<SaleItem> items) {
        return Flux.fromIterable(items)
                .flatMapSequential(item ->
                        medicationClient.findByIdNoStockCheck(item.getMedicationId())
                                .flatMap(med ->
                                        medicationClient.decreaseStock(
                                                item.getMedicationId(),
                                                med.getStock(),
                                                -item.getQuantity()
                                        )
                                )
                )
                .then();
    }

    /**
     * Valida que haya stock suficiente antes de restaurar una venta revocada.
     */
    private Mono<Void> validateStock(List<SaleItem> items) {
        return Flux.fromIterable(items)
                .flatMapSequential(item ->
                        medicationClient.findById(item.getMedicationId())
                                .flatMap(med -> {
                                    if (med.getStock() != null && med.getStock() < item.getQuantity()) {
                                        return Mono.error(new DomainException(
                                                "Stock insuficiente para '" + med.getGenericName() +
                                                "'. Disponible: " + med.getStock() +
                                                ", requerido: " + item.getQuantity()));
                                    }
                                    return Mono.empty();
                                })
                )
                .then();
    }

    /**
     * Descuenta stock al restaurar una venta revocada.
     */
    private Mono<Void> decreaseStockForAll(List<SaleItem> items) {
        return Flux.fromIterable(items)
                .flatMapSequential(item ->
                        medicationClient.findById(item.getMedicationId())
                                .flatMap(med ->
                                        medicationClient.decreaseStock(
                                                item.getMedicationId(),
                                                med.getStock(),
                                                item.getQuantity()
                                        )
                                )
                )
                .then();
    }
}
