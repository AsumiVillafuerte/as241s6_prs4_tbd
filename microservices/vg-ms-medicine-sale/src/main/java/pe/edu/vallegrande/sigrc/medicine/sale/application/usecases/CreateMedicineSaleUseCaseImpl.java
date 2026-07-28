package pe.edu.vallegrande.sigrc.medicine.sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.SaleItem;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.in.ICreateMedicineSaleUseCase;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.out.IMedicineSaleRepository;
import pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients.MedicationClient;
import pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients.MedicationClientResponse;
import pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients.PatientClient;
import pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients.UserClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMedicineSaleUseCaseImpl implements ICreateMedicineSaleUseCase {

    private final IMedicineSaleRepository repository;
    private final MedicationClient medicationClient;
    private final PatientClient patientClient;
    private final UserClient userClient;

    @Override
    public Mono<MedicineSale> create(MedicineSale sale) {
        log.debug("Creando nueva venta - paciente: {}, cajero: {}", sale.getPatientId(), sale.getCashierId());

        // 1. Validar paciente y cajero en paralelo
        return Mono.zip(
                        patientClient.findById(sale.getPatientId()),
                        userClient.findCashierById(sale.getCashierId())
                )
                .flatMap(tuple -> {
                    var patient = tuple.getT1();
                    var cashier = tuple.getT2();

                    // Enriquecer la venta con datos del paciente y cajero
                    sale.setPatientName(patient.getFirstName() + " " + patient.getLastName());
                    sale.setDni(patient.getDocumentNumber());
                    sale.setCashierName(cashier.getFirstName() + " " + cashier.getLastName());

                    // 2. Validar y enriquecer items con datos de medicamentos
                    return fetchAndValidateItems(sale.getItems());
                })
                .flatMap(enrichedItems -> {
                    sale.setItems(enrichedItems);

                    // 3. Calcular total
                    BigDecimal total = enrichedItems.stream()
                            .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    sale.setTotal(total);
                    sale.setSaleDate(LocalDateTime.now());
                    // Usar el status del request, por defecto VENDIDO
                    if (sale.getStatus() == null) {
                        sale.setStatus(SaleStatus.CONSIGNADO);
                    }

                    // 4. Generar ticket y guardar
                    return generateTicket()
                            .flatMap(ticket -> {
                                sale.setTicket(ticket);
                                return repository.save(sale);
                            });
                })
                // 5. Descontar stock en ms-medications
                .flatMap(savedSale ->
                        decreaseStockForAll(savedSale.getItems())
                                .thenReturn(savedSale)
                );
    }

    private Mono<List<SaleItem>> fetchAndValidateItems(List<SaleItem> items) {
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

    private SaleItem buildEnrichedItem(SaleItem item, MedicationClientResponse med) {
        String medName = med.getGenericName() != null
                ? med.getGenericName()
                : med.getCommercialName();

        return SaleItem.builder()
                .medicationId(med.getId())
                .medicationName(item.getMedicationName() != null ? item.getMedicationName() : medName)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.valueOf(med.getSalePrice()))
                .build();
    }

    private Mono<String> generateTicket() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String random = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 999999));
        String candidate = "FAR-" + date + "-" + random;

        return repository.existsByTicket(candidate)
                .flatMap(exists -> exists ? generateTicket() : Mono.just(candidate));
    }
}
