package pe.edu.vallegrande.sigrc.shopping.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DetalleCompraRequest(

        @NotBlank(message = "El medicamento es obligatorio")
        String medicamentoId,

        @NotBlank(message = "El laboratorio es obligatorio")
        String laboratorio,

        @NotBlank(message = "La presentación es obligatoria")
        String presentacion,

        @NotBlank(message = "El lote es obligatorio")
        String lote,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        @Future(message = "La fecha de vencimiento debe ser futura")
        LocalDate vencimiento,

        String ubicacion,

        @NotNull(message = "El precio de compra es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio de compra no puede ser negativo")
        BigDecimal precioCompra,

        @DecimalMin(value = "0.0", message = "El markup no puede ser negativo")
        BigDecimal markupPorcentaje,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a 0")
        Integer cantidad,

        @NotNull(message = "El stock mínimo es obligatorio")
        @Min(value = 1, message = "El stock mínimo debe ser mayor a 0")
        Integer minStock
) {}
