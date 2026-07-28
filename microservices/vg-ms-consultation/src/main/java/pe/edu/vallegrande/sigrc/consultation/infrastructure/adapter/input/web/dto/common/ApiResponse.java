package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.common;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data
) {}
