package pe.edu.vallegrande.sigrc.shopping.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateCompraRequest(

        @NotBlank(message = "El número de comprobante es obligatorio")
        String numeroComprobante,

        @NotBlank(message = "El ID de usuario es obligatorio")
        String usuarioId,

        @NotNull(message = "El proveedor es obligatorio")
        Long proveedorId,

        @NotEmpty(message = "Debe incluir al menos un medicamento")
        List<@Valid DetalleCompraRequest> detalles
) {}
