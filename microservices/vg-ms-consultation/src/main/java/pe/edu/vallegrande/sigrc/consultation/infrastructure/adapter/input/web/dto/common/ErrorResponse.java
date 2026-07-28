package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.common;

import java.time.LocalDateTime;

public record ErrorResponse(
    String error,
    String message,
    int status,
    LocalDateTime timestamp
) {}
