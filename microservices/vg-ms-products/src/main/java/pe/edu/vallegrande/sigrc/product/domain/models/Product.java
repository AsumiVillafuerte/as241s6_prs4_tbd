package pe.edu.vallegrande.sigrc.product.domain.models;

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
public class Product {
    private UUID id;
    private String commercialName;
    private UUID brandId;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Integer stock;
    private String location;
    private Character status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
