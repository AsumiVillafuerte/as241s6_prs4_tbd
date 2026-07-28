package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultationPriceResponse(
    String id,
    String specialtyId,
    BigDecimal price,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
