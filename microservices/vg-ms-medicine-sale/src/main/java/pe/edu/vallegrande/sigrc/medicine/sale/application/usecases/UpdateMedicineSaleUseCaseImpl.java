package pe.edu.vallegrande.sigrc.medicine.sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.SaleItem;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.IUpdateMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.out.IMedicineSaleRepository;
import pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients.MedicationClient;
import pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients.MedicationClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateMedicineSaleUseCaseImpl implements IUpdateMedicineSaleUseCase {

    private final IMedicineSaleRepository repository;
    private final MedicationClient medicationClient;

    @Override
    public Mono<MedicineSale> update(String id, MedicineSale sale) {
        log.debug("Actualizando venta con id: {}", id);
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException.forId(id)))
                .flatMap(existing -> {
                    // No se puede editar una venta revocada o eliminada
                    if (SaleStatus.REVOCADO.equals(existing.getStatus())) {
                        return Mono.error(new DomainException(
                                "No se puede editar una venta REVOCADA. Usa /restore para restaurarla primero."));
                    }
                    if (SaleStatus.INACTIVE.equals(existing.getStatus())) {
                        return Mono.error(new DomainException(
                                "No se puede editar una venta ELIMINADA. Usa /restore para restaurarla primero."));
                    }

                    // Determinar el nuevo status con validaciones de transición
                    SaleStatus newStatus = sale.getStatus() != null ? sale.getStatus() : existing.getStatus();

                    if (!existing.getStatus().equals(newStatus)) {
                        if (SaleStatus.REVOCADO.equals(existing.getStatus())) {
                            return Mono.error(new DomainException(
                                    "No se puede editar una venta REVOCADA."));
                        }
                    }

                    final SaleStatus finalStatus = newStatus;

                    return enrichItems(sale.getItems())
                            .flatMap(enrichedItems -> {
                                // Campos inmutables
                                sale.setId(existing.getId());
                                sale.setTicket(existing.getTicket());
                                sale.setSaleDate(existing.getSaleDate());
                                sale.setStatus(finalStatus);

                                // ✅ Preservar IDs y nombres del paciente/cajero
                                if (sale.getPatientId() == null || sale.getPatientId().isEmpty()) {
                                    sale.setPatientId(existing.getPatientId());
                                }
                                if (sale.getPatientName() == null || sale.getPatientName().isEmpty()) {
                                    sale.setPatientName(existing.getPatientName());
                                }
                                if (sale.getDni() == null || sale.getDni().isEmpty()) {
                                    sale.setDni(existing.getDni());
                                }
                                if (sale.getCashierId() == null || sale.getCashierId().isEmpty()) {
                                    sale.setCashierId(existing.getCashierId());
                                }
                                if (sale.getCashierName() == null || sale.getCashierName().isEmpty()) {
                                    sale.setCashierName(existing.getCashierName());
                                }

                                sale.setItems(enrichedItems);

                                BigDecimal total = enrichedItems.stream()
                                        .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                                sale.setTotal(total);

                                log.info("Venta actualizada: id={}, status={}, total={}",
                                        sale.getId(), sale.getStatus(), sale.getTotal());

                                return repository.save(sale);
                            });
                });
    }

    private Mono<List<SaleItem>> enrichItems(List<SaleItem> items) {
        return Flux.fromIterable(items)
                .flatMapSequential(item ->
                        medicationClient.findById(item.getMedicationId())
                                .flatMap(med -> {
                                    if (med.getStock() != null && med.getStock() < item.getQuantity()) {
                                        return Mono.error(new DomainException(
                                                "Stock insuficiente para '" + med.getGenericName() +
                                                "'. Disponible: " + med.getStock() +
                                                ", solicitado: " + item.getQuantity()));
                                    }
                                    return Mono.just(buildEnrichedItem(item, med));
                                })
                )
                .collectList();
    }

    private SaleItem buildEnrichedItem(SaleItem item, MedicationClientResponse med) {
        String medName = med.getGenericName() != null
                ? med.getGenericName()
                : med.getCommercialName();

        return SaleItem.builder()
                .medicationId(med.getId())
                .medicationName(
                        item.getMedicationName() != null
                                ? item.getMedicationName()
                                : medName)
                .quantity(item.getQuantity())
                .unitPrice(
                        item.getUnitPrice() != null
                                ? item.getUnitPrice()
                                : BigDecimal.valueOf(med.getSalePrice()))
                .build();
    }
}
