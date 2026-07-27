package pe.edu.vallegrande.sigrc.product_sale.application.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleTypeRequest {

    @Pattern(regexp = "(?i)Vendido|Donado|Donacion|Donada|Donaci\u00f3n", message = "El tipo de venta debe ser 'Vendido' o 'Donacion'")
    private String saleType;
}
