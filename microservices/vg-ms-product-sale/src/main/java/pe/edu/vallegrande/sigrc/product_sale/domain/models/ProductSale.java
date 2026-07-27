package pe.edu.vallegrande.sigrc.product_sale.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSale {
    private UUID id;
    private String ticketNumber;
    private UUID patientId;
    private String userId;
    private BigDecimal total;
    private String saleType;
    private String status;
    private LocalDate saleDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Relación con los ítems (no persistida directamente, se carga por separado)
    private List<ProductSaleItem> items;
}
