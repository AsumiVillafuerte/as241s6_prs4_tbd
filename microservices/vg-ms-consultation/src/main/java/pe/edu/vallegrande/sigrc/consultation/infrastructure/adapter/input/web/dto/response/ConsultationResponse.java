package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultationResponse(
    String id,
    String ticket,
    String specialtyId,
    String medic,
    String medicName,
    String patient,
    String patientName,
    String dni,
    String registerBy,
    String registerByName,
    BigDecimal total,
    String tipo,
    String tarjeta,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
