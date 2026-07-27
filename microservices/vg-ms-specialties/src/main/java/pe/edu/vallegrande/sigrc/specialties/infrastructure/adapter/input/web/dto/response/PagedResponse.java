package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.response;

import java.util.List;

public record PagedResponse<T>(
    List<T> content,
    long totalElements,
    int totalPages,
    int number,
    int size
) {}
