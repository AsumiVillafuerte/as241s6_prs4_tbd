package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("detalle_compra_medicamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraMedicamentoEntity {

    @Id
    private Long id;
    private Long compraId;
    private String medicamentoId;
    private String denominacionComercial;
    private String denominacionGenerica;
    private String laboratorio;
    private String presentacion;
    private String lote;
    private LocalDate vencimiento;
    private String ubicacion;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer cantidad;
    private Integer minStock;
    private BigDecimal total;
    private BigDecimal markupPorcentaje;
    private String loteInventarioId;
    private LocalDateTime createdAt;
}
