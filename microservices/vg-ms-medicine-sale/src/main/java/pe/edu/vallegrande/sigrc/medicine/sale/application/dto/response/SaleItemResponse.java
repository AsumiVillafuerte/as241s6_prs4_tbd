package pe.edu.vallegrande.sigrc.medicine.sale.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemResponse {

    private String medicationId;
    private String medicationName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
