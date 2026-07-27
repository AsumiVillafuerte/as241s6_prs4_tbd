package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client.dto;

public record MedicamentosApiResponse<T>(
        Integer status,
        String message,
        T data
) {}
