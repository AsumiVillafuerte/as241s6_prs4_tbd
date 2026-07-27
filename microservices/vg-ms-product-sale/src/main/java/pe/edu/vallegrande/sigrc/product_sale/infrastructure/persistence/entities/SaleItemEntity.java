package pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sale_items", schema = "public")
public class SaleItemEntity {

    @Id
    private UUID id;

    @Column("sale_id")
    private UUID saleId;

    @Column("product_id")
    private UUID productId;

    @Column("quantity")
    private Integer quantity;

    @Column("unit_price")
    private BigDecimal unitPrice;

    // subtotal es una columna generada (GENERATED ALWAYS AS), solo lectura
    @ReadOnlyProperty
    @Column("subtotal")
    private BigDecimal subtotal;
}
