package pe.edu.vallegrande.sigrc.product.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class ProductEntity {
    
    @Id
    private UUID id;
    
    @Column("commercial_name")
    private String commercialName;
    
    @Column("brand_id")
    private UUID brandId;
    
    @Column("purchase_price")
    private BigDecimal purchasePrice;
    
    @Column("sale_price")
    private BigDecimal salePrice;
    
    @Column("stock")
    private Integer stock;
    
    @Column("location")
    private String location;
    
    @Column("status")
    private String status;
    
    @Column("created_at")
    private LocalDateTime createdAt;
    
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
