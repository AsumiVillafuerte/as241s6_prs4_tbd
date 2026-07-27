package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.specialties.domain.model.ClientType;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Specialty;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.entity.ClientTypeDocument;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.entity.SpecialtyDocument;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.entity.TreatmentDocument;

@Component
public class PersistenceMapper {

    // --- SPECIALTY MAPPER ---
    public Specialty toDomain(SpecialtyDocument doc) {
        if (doc == null) return null;
        return Specialty.builder()
                .id(doc.getId())
                .code(doc.getCode())
                .name(doc.getName())
                .description(doc.getDescription())
                .color(doc.getColor())
                .status(CommonStatus.fromString(doc.getStatus())) // Traduce String a Enum de forma segura
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }

    public SpecialtyDocument toEntity(Specialty domain) {
        if (domain == null) return null;
        return new SpecialtyDocument(
                domain.getId(),
                domain.getCode(),
                domain.getName(),
                domain.getDescription(),
                domain.getColor(),
                domain.getStatus() != null ? domain.getStatus().name() : null, // Guarda como String
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    // --- TREATMENT MAPPER ---
    public Treatment toDomain(TreatmentDocument doc) {
        if (doc == null) return null;
        return Treatment.builder()
                .id(doc.getId())
                .code(doc.getCode())
                .name(doc.getName())
                .specialtyId(doc.getSpecialtyId())
                .status(CommonStatus.fromString(doc.getStatus()))
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }

    public TreatmentDocument toEntity(Treatment domain) {
        if (domain == null) return null;
        return new TreatmentDocument(
                domain.getId(),
                domain.getCode(),
                domain.getName(),
                domain.getSpecialtyId(),
                domain.getStatus() != null ? domain.getStatus().name() : null,
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    // --- CLIENT TYPE MAPPER ---
    public ClientType toDomain(ClientTypeDocument doc) {
        if (doc == null) return null;
        return ClientType.builder()
                .id(doc.getId())
                .name(doc.getName())
                .description(doc.getDescription())
                .status(CommonStatus.fromString(doc.getStatus()))
                .createdAt(doc.getCreatedAt())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }

    public ClientTypeDocument toEntity(ClientType domain) {
        if (domain == null) return null;
        return new ClientTypeDocument(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getStatus() != null ? domain.getStatus().name() : null,
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
