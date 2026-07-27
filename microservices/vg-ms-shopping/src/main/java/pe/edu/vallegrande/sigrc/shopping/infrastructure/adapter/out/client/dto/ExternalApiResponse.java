package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client.dto;

public record ExternalApiResponse<T>(
        boolean success,
        String message,
        T data
) {}
