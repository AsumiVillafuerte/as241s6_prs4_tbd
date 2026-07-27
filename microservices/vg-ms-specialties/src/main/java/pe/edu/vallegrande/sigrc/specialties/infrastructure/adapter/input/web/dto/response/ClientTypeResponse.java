package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response;

import java.time.LocalDateTime;

public record ClientTypeResponse(
    String id,
    String name,
    String description,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
