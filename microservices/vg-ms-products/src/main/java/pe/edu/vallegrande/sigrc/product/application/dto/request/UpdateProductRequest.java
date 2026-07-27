package pe.edu.vallegrande.sigrc.product.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un producto existente")
public class UpdateProductRequest {
    
    @Size(max = 150, message = "El nombre comercial no puede exceder 150 caracteres")
    private String commercialName;
    
    private UUID brandId;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio de compra debe ser mayor o igual a 0")
    private BigDecimal purchasePrice;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio de venta debe ser mayor o igual a 0")
    private BigDecimal salePrice;
    
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;
    
    @Size(max = 50, message = "La ubicación no puede exceder 50 caracteres")
    private String location;
}
