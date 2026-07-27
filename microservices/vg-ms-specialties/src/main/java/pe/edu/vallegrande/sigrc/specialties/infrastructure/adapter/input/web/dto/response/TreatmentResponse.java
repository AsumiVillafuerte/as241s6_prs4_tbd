package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response;

import java.time.LocalDateTime;

public record TreatmentResponse(
    String id,
    String code,
    String name,
    String specialtyId,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
