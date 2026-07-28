package pe.edu.vallegrande.sigrc.medications.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;
import pe.edu.vallegrande.sigrc.medications.domain.ports.out.IMedicationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MedicationRepositoryAdapter implements IMedicationRepository {

    private final MedicationReactiveRepository repository;

    @Override
    public Flux<Medication> findAll() {
        return repository.findByStatus("active").map(this::toDomain);
    }

    @Override
    public Flux<Medication> findAllInactive() {
        return repository.findByStatus("inactive").map(this::toDomain);
    }

    @Override
    public Mono<Medication> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<Medication> findByCode(String code) {
        return repository.findByCode(code.toUpperCase()).map(this::toDomain);
    }

    @Override
    public Mono<Medication> findByCodeAndBatchNumber(String code, String batchNumber) {
        return repository.findByCodeAndBatchNumber(code.toUpperCase(), batchNumber).map(this::toDomain);
    }

    @Override
    public Mono<Medication> save(Medication medication) {
        return repository.save(toDocument(medication)).map(this::toDomain);
    }

    @Override
    public Mono<Medication> update(String id, Medication medication) {
        return repository.findById(id)
                .flatMap(doc -> {
                    if (medication.getGenericName() != null) doc.setGenericName(medication.getGenericName());
                    if (medication.getCommercialName() != null) doc.setCommercialName(medication.getCommercialName());
                    if (medication.getCategory() != null) doc.setCategory(medication.getCategory());
                    if (medication.getForm() != null) doc.setForm(medication.getForm());
                    if (medication.getStock() != null) doc.setStock(medication.getStock());
                    if (medication.getMinStock() != null) doc.setMinStock(medication.getMinStock());
                    if (medication.getDescription() != null) doc.setDescription(medication.getDescription());
                    if (medication.getSideEffects() != null) doc.setSideEffects(medication.getSideEffects());
                    if (medication.getRequiresPrescription() != null) doc.setRequiresPrescription(medication.getRequiresPrescription());
                    if (medication.getSupplierId() != null) doc.setSupplierId(medication.getSupplierId());
                    if (medication.getLocation() != null) doc.setLocation(medication.getLocation());
                    if (medication.getLaboratory() != null) doc.setLaboratory(medication.getLaboratory());
                    if (medication.getCostPrice() != null) doc.setCostPrice(medication.getCostPrice());
                    if (medication.getSalePrice() != null) doc.setSalePrice(medication.getSalePrice());
                    if (medication.getExpirationDate() != null) doc.setExpirationDate(medication.getExpirationDate());
                    if (medication.getBatchNumber() != null) doc.setBatchNumber(medication.getBatchNumber());
                    return repository.save(doc);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Medication> changeStatus(String id, String status) {
        return repository.findById(id)
                .flatMap(doc -> {
                    doc.setStatus(status);
                    return repository.save(doc);
                })
                .map(this::toDomain);
    }

    private Medication toDomain(MedicationDocument doc) {
        return Medication.builder()
                .id(doc.getId())
                .code(doc.getCode())
                .genericName(doc.getGenericName())
                .commercialName(doc.getCommercialName())
                .category(doc.getCategory())
                .form(doc.getForm())
                .stock(doc.getStock())
                .minStock(doc.getMinStock())
                .description(doc.getDescription())
                .sideEffects(doc.getSideEffects())
                .requiresPrescription(doc.getRequiresPrescription())
                .supplierId(doc.getSupplierId())
                .location(doc.getLocation())
                .laboratory(doc.getLaboratory())
                .costPrice(doc.getCostPrice())
                .salePrice(doc.getSalePrice())
                .expirationDate(doc.getExpirationDate())
                .batchNumber(doc.getBatchNumber())
                .status(doc.getStatus())
                .build();
    }

    private MedicationDocument toDocument(Medication medication) {
        return MedicationDocument.builder()
                .code(medication.getCode() != null ? medication.getCode().toUpperCase() : null)
                .genericName(medication.getGenericName())
                .commercialName(medication.getCommercialName())
                .category(medication.getCategory())
                .form(medication.getForm())
                .stock(medication.getStock())
                .minStock(medication.getMinStock())
                .description(medication.getDescription())
                .sideEffects(medication.getSideEffects())
                .requiresPrescription(medication.getRequiresPrescription())
                .supplierId(medication.getSupplierId())
                .location(medication.getLocation())
                .laboratory(medication.getLaboratory())
                .costPrice(medication.getCostPrice())
                .salePrice(medication.getSalePrice())
                .expirationDate(medication.getExpirationDate())
                .batchNumber(medication.getBatchNumber())
                .status(medication.getStatus())
                .build();
    }
}
