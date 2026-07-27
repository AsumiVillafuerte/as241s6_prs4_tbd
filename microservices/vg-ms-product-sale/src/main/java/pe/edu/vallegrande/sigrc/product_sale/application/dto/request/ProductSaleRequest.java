package pe.edu.vallegrande.sigrc.product_sale.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleRequest {

    private UUID patientId;

    @Size(max = 50, message = "El ID del usuario no puede exceder 50 caracteres")
    private String userId;

    @Pattern(regexp = "(?i)Vendido|Donado|Donacion|Donada|Donaci\u00f3n", message = "El tipo de venta debe ser 'Vendido', 'Donado' o 'Donacion'")
    private String saleType = "Vendido";

    @Valid
    private List<ProductItemRequest> items;
}
