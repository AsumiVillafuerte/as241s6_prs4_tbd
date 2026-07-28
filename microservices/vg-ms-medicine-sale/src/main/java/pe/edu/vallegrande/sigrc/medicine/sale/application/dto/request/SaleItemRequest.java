package pe.edu.vallegrande.sigrc.medicine.sale.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemRequest {

    @NotBlank(message = "El ID del medicamento es requerido")
    private String medicationId;

    // Opcional: si no se envía, se obtiene automáticamente del microservicio de medicamentos
    private String medicationName;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;

    // Opcional: si no se envía, se usa el salePrice del microservicio de medicamentos
    private BigDecimal unitPrice;
}
