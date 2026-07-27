package pe.edu.vallegrande.sigrc.product.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta con los datos de un producto")
public class ProductResponse {
    private UUID id;
    private String commercialName;
    private UUID brandId;
    private String brandName;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Integer stock;
    private String location;
    private Character status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
