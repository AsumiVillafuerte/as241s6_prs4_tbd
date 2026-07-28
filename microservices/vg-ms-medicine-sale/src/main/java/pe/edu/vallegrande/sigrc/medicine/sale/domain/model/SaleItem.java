package pe.edu.vallegrande.sigrc.medicine.sale.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {

    private String medicationId;
    private String medicationName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
