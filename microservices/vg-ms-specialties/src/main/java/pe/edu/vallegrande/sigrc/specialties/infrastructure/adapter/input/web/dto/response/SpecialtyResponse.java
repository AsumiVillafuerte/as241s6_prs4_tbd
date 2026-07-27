package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response;

import java.time.LocalDateTime;

public record SpecialtyResponse(
    String id,
    String code,
    String name,
    String description,
    String color,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
