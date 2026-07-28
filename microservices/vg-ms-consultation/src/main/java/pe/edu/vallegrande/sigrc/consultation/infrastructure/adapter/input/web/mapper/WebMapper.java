package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.consultation.domain.model.Consultation;
import pe.edu.vallegrande.sigrc.consultation.domain.model.ConsultationPrice;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.request.ConsultationPriceRequest;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.request.ConsultationRequest;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response.ConsultationPriceResponse;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response.ConsultationResponse;

@Component
public class WebMapper {

    // --- TO DOMAIN ---
    public Consultation toDomain(ConsultationRequest req) {
        if (req == null) return null;
        return Consultation.builder()
                .specialtyId(req.specialtyId())
                .medic(req.medic())
                .medicName(req.medicName())
                .patient(req.patient())
                .patientName(req.patientName())
                .dni(req.dni())
                .registerBy(req.registerBy())
                .registerByName(req.registerByName())
                .total(req.total())
                .tipo(req.tipo())
                .tarjeta(req.tarjeta())
                .build();
    }

    public ConsultationPrice toDomain(ConsultationPriceRequest req) {
        if (req == null) return null;
        return ConsultationPrice.builder()
                .specialtyId(req.specialtyId())
                .price(req.price())
                .build();
    }

    // --- TO RESPONSE ---
    public ConsultationResponse toResponse(Consultation domain) {
        if (domain == null) return null;
        return new ConsultationResponse(
                domain.getId(),
                domain.getTicket(),
                domain.getSpecialtyId(),
                domain.getMedic(),
                domain.getMedicName(),
                domain.getPatient(),
                domain.getPatientName(),
                domain.getDni(),
                domain.getRegisterBy(),
                domain.getRegisterByName(),
                domain.getTotal(),
                domain.getTipo(),
                domain.getTarjeta(),
                domain.getStatus(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    public ConsultationPriceResponse toResponse(ConsultationPrice domain) {
        if (domain == null) return null;
        return new ConsultationPriceResponse(
                domain.getId(),
                domain.getSpecialtyId(),
                domain.getPrice(),
                domain.getStatus(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
