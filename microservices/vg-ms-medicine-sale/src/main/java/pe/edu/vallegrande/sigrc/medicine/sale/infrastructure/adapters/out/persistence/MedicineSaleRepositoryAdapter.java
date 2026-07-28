package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.SaleItem;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.ports.out.IMedicineSaleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicineSaleRepositoryAdapter implements IMedicineSaleRepository {

    private final MedicineSaleReactiveRepository repository;

    @Override
    public Flux<MedicineSale> findAll() {
        // Solo devuelve las ventas que NO están eliminadas lógicamente
        return repository.findByStatusNot(SaleStatus.INACTIVE.name()).map(this::toDomain);
    }

    @Override
    public Flux<MedicineSale> findAllInactive() {
        return repository.findByStatus(SaleStatus.INACTIVE.name()).map(this::toDomain);
    }

    @Override
    public Flux<MedicineSale> findByStatus(String status) {
        return repository.findByStatus(status).map(this::toDomain);
    }

    @Override
    public Mono<MedicineSale> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<MedicineSale> findByTicket(String ticket) {
        return repository.findByTicket(ticket).map(this::toDomain);
    }

    @Override
    public Flux<MedicineSale> findByDni(String dni) {
        return repository.findByDni(dni).map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByTicket(String ticket) {
        return repository.existsByTicket(ticket);
    }

    @Override
    public Mono<MedicineSale> save(MedicineSale sale) {
        return repository.save(toDocument(sale)).map(this::toDomain);
    }

    @Override
    public Mono<MedicineSale> changeStatus(String id, String status) {
        return repository.findById(id)
                .flatMap(doc -> {
                    doc.setStatus(SaleStatus.valueOf(status));
                    return repository.save(doc);
                })
                .map(this::toDomain);
    }

    // ── Mapeo Document ↔ Domain ─────────────────────────────────────────────

    private MedicineSale toDomain(MedicineSaleDocument doc) {
        return MedicineSale.builder()
                .id(doc.getId())
                .ticket(doc.getTicket())
                .saleDate(doc.getSaleDate())
                .patientId(doc.getPatientId())
                .patientName(doc.getPatientName())
                .dni(doc.getDni())
                .cashierId(doc.getCashierId())
                .cashierName(doc.getCashierName())
                .total(doc.getTotal())
                .type(doc.getType())
                .status(doc.getStatus())
                .items(toItemDomainList(doc.getItems()))
                .build();
    }

    private MedicineSaleDocument toDocument(MedicineSale sale) {
        return MedicineSaleDocument.builder()
                .id(sale.getId())
                .ticket(sale.getTicket())
                .saleDate(sale.getSaleDate())
                .patientId(sale.getPatientId())
                .patientName(sale.getPatientName())
                .dni(sale.getDni())
                .cashierId(sale.getCashierId())
                .cashierName(sale.getCashierName())
                .total(sale.getTotal())
                .type(sale.getType())
                .status(sale.getStatus())
                .items(toItemDocumentList(sale.getItems()))
                .build();
    }

    private List<SaleItem> toItemDomainList(List<SaleItemDocument> docs) {
        if (docs == null) return List.of();
        return docs.stream()
                .map(doc -> SaleItem.builder()
                        .medicationId(doc.getMedicationId())
                        .medicationName(doc.getMedicationName())
                        .quantity(doc.getQuantity())
                        .unitPrice(doc.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }

    private List<SaleItemDocument> toItemDocumentList(List<SaleItem> items) {
        if (items == null) return List.of();
        return items.stream()
                .map(item -> SaleItemDocument.builder()
                        .medicationId(item.getMedicationId())
                        .medicationName(item.getMedicationName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
