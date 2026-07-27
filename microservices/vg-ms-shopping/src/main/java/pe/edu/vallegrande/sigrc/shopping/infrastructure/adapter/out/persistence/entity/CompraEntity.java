package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("compras")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraEntity {

    @Id
    private Long id;
    private String numeroComprobante;
    private Long proveedorId;
    private String usuarioId;
    private String tipo;
    private String estado;
    private BigDecimal precioCompraTotal;
    private BigDecimal precioVentaTotal;
    private LocalDateTime fechaCompra;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
