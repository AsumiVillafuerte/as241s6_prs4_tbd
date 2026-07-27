package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.specialties.domain.model.ClientType;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Specialty;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request.ClientTypeRequest;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request.SpecialtyRequest;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request.TreatmentRequest;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.ClientTypeResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.SpecialtyResponse;
import pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response.TreatmentResponse;

@Component
public class WebMapper {

    // --- TO DOMAIN ---
    public Specialty toDomain(SpecialtyRequest req) {
        if (req == null) return null;
        return Specialty.builder()
                .code(req.code())
                .name(req.name())
                .description(req.description())
                .color(req.color())
                .build();
    }

    public Treatment toDomain(TreatmentRequest req) {
        if (req == null) return null;
        return Treatment.builder()
                .code(req.code())
                .name(req.name())
                .specialtyId(req.specialtyId())
                .build();
    }

    public ClientType toDomain(ClientTypeRequest req) {
        if (req == null) return null;
        return ClientType.builder()
                .name(req.name())
                .description(req.description())
                .build();
    }

    // --- TO RESPONSE ---
    public SpecialtyResponse toResponse(Specialty domain) {
        if (domain == null) return null;
        return new SpecialtyResponse(
                domain.getId(),
                domain.getCode(),
                domain.getName(),
                domain.getDescription(),
                domain.getColor(),
                domain.getStatus() != null ? domain.getStatus().name() : null,
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    public TreatmentResponse toResponse(Treatment domain) {
        if (domain == null) return null;
        return new TreatmentResponse(
                domain.getId(),
                domain.getCode(),
                domain.getName(),
                domain.getSpecialtyId(),
                domain.getStatus() != null ? domain.getStatus().name() : null,
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    public ClientTypeResponse toResponse(ClientType domain) {
        if (domain == null) return null;
        return new ClientTypeResponse(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getStatus() != null ? domain.getStatus().name() : null,
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
