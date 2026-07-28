package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.mapper;

import pe.edu.vallegrande.sigrc.consultation.domain.model.Consultation;
import pe.edu.vallegrande.sigrc.consultation.domain.model.ConsultationPrice;
import pe.edu.vallegrande.sigrc.consultation.domain.model.TicketSequence;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity.ConsultationEntity;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity.ConsultationPriceEntity;
import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity.TicketSequenceEntity;
import org.springframework.stereotype.Component;

@Component
public class PersistenceMapper {

    public ConsultationEntity toEntity(Consultation domain) {
        return ConsultationEntity.builder()
            .id(domain.getId())
            .ticket(domain.getTicket())
            .specialtyId(domain.getSpecialtyId())
            .medic(domain.getMedic())
            .medicName(domain.getMedicName())
            .patient(domain.getPatient())
            .patientName(domain.getPatientName())
            .dni(domain.getDni())
            .registerBy(domain.getRegisterBy())
            .registerByName(domain.getRegisterByName())
            .total(domain.getTotal())
            .tipo(domain.getTipo())
            .tarjeta(domain.getTarjeta())
            .status(domain.getStatus())
            .version(domain.getVersion())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    public Consultation toDomain(ConsultationEntity entity) {
        return Consultation.builder()
            .id(entity.getId())
            .ticket(entity.getTicket())
            .specialtyId(entity.getSpecialtyId())
            .medic(entity.getMedic())
            .medicName(entity.getMedicName())
            .patient(entity.getPatient())
            .patientName(entity.getPatientName())
            .dni(entity.getDni())
            .registerBy(entity.getRegisterBy())
            .registerByName(entity.getRegisterByName())
            .total(entity.getTotal())
            .tipo(entity.getTipo())
            .tarjeta(entity.getTarjeta())
            .status(entity.getStatus())
            .version(entity.getVersion())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public ConsultationPriceEntity toEntity(ConsultationPrice domain) {
        return ConsultationPriceEntity.builder()
            .id(domain.getId())
            .specialtyId(domain.getSpecialtyId())
            .price(domain.getPrice())
            .status(domain.getStatus())
            .version(domain.getVersion())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    public ConsultationPrice toDomain(ConsultationPriceEntity entity) {
        return ConsultationPrice.builder()
            .id(entity.getId())
            .specialtyId(entity.getSpecialtyId())
            .price(entity.getPrice())
            .status(entity.getStatus())
            .version(entity.getVersion())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public TicketSequenceEntity toEntity(TicketSequence domain) {
        return TicketSequenceEntity.builder()
            .date(domain.getDate())
            .sequence(domain.getSequence())
            .version(domain.getVersion())
            .build();
    }

    public TicketSequence toDomain(TicketSequenceEntity entity) {
        return TicketSequence.builder()
            .date(entity.getDate())
            .sequence(entity.getSequence())
            .version(entity.getVersion())
            .build();
    }
}
