package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.mapper;

import pe.edu.vallegrande.sigrc.therapies.domain.model.*;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PersistenceMapper {

    public TherapyDocument toDocument(Therapy domain) {
        return TherapyDocument.builder()
            .id(domain.getId())
            .ticket(domain.getTicket())
            .specialtyId(domain.getSpecialtyId())
            .clientType(domain.getClientType())
            .medic(domain.getMedic())
            .medicName(domain.getMedicName())
            .patient(domain.getPatient())
            .patientName(domain.getPatientName())
            .dni(domain.getDni())
            .registerBy(domain.getRegisterBy())
            .registerByName(domain.getRegisterByName())
            .total(domain.getTotal())
            .type(domain.getType())
            .tarjeta(domain.getTarjeta())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    public Therapy toDomain(TherapyDocument doc) {
        return Therapy.builder()
            .id(doc.getId())
            .ticket(doc.getTicket())
            .specialtyId(doc.getSpecialtyId())
            .clientType(doc.getClientType())
            .medic(doc.getMedic())
            .medicName(doc.getMedicName())
            .patient(doc.getPatient())
            .patientName(doc.getPatientName())
            .dni(doc.getDni())
            .registerBy(doc.getRegisterBy())
            .registerByName(doc.getRegisterByName())
            .total(doc.getTotal())
            .type(doc.getType())
            .tarjeta(doc.getTarjeta())
            .status(doc.getStatus())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build();
    }

    public TherapyPriceDocument toDocument(TherapyPrice domain) {
        return TherapyPriceDocument.builder()
            .id(domain.getId())
            .specialtyId(domain.getSpecialtyId())
            .clientType(domain.getClientType())
            .price(domain.getPrice())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    public TherapyPrice toDomain(TherapyPriceDocument doc) {
        return TherapyPrice.builder()
            .id(doc.getId())
            .specialtyId(doc.getSpecialtyId())
            .clientType(doc.getClientType())
            .price(doc.getPrice())
            .status(doc.getStatus())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build();
    }

    public TreatmentDocument toDocument(Treatment domain) {
        return TreatmentDocument.builder()
            .id(domain.getId())
            .code(domain.getCode())
            .name(domain.getName())
            .specialtyId(domain.getSpecialtyId())
            .salePrice(domain.getSalePrice())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    public Treatment toDomain(TreatmentDocument doc) {
        return Treatment.builder()
            .id(doc.getId())
            .code(doc.getCode())
            .name(doc.getName())
            .specialtyId(doc.getSpecialtyId())
            .salePrice(doc.getSalePrice())
            .status(doc.getStatus())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build();
    }

    public LabTestDocument toDocument(LabTest domain) {
        return LabTestDocument.builder()
            .id(domain.getId())
            .name(domain.getName())
            .price(domain.getPrice())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    public LabTest toDomain(LabTestDocument doc) {
        return LabTest.builder()
            .id(doc.getId())
            .name(doc.getName())
            .price(doc.getPrice())
            .status(doc.getStatus())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build();
    }

    public LabKitDocument toDocument(LabKit domain) {
        return LabKitDocument.builder()
            .id(domain.getId())
            .name(domain.getName())
            .items(domain.getItems() != null
                ? domain.getItems().stream().map(this::toItemDocument).collect(Collectors.toList())
                : null)
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    private LabKitItemDocument toItemDocument(LabKitItem item) {
        return LabKitItemDocument.builder()
            .labTestId(item.getLabTestId())
            .prueba(item.getPrueba())
            .labTestName(item.getLabTestName())
            .labTestPrice(item.getLabTestPrice())
            .build();
    }

    public LabKit toDomain(LabKitDocument doc) {
        return LabKit.builder()
            .id(doc.getId())
            .name(doc.getName())
            .items(doc.getItems() != null
                ? doc.getItems().stream().map(this::toItemDomain).collect(Collectors.toList())
                : null)
            .status(doc.getStatus())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build();
    }

    private LabKitItem toItemDomain(LabKitItemDocument doc) {
        return LabKitItem.builder()
            .labTestId(doc.getLabTestId())
            .prueba(doc.getPrueba())
            .labTestName(doc.getLabTestName())
            .labTestPrice(doc.getLabTestPrice())
            .build();
    }

    public TicketSequenceDocument toDocument(TicketSequence domain) {
        return TicketSequenceDocument.builder()
            .date(domain.getDate())
            .sequence(domain.getSequence())
            .build();
    }

    public TicketSequence toDomain(TicketSequenceDocument doc) {
        return TicketSequence.builder()
            .date(doc.getDate())
            .sequence(doc.getSequence())
            .build();
    }
}
