package pe.edu.vallegrande.sigrc.product_sale.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private String id;
    private String commercialName;
    private BigDecimal salePrice;
    private Integer stock;
    private Character status;
}
