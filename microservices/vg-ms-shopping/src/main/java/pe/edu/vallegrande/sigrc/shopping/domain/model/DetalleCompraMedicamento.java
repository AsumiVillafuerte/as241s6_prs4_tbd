package pe.edu.vallegrande.sigrc.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraMedicamento {

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

    @Transient
    private MedicamentoInfo medicamentoInfo;

    private static final BigDecimal MARKUP_DEFAULT = new BigDecimal("30.00");

    public void calcularTotal() {
        BigDecimal markup = markupPorcentaje != null ? markupPorcentaje : MARKUP_DEFAULT;
        this.markupPorcentaje = markup;
        this.precioVenta = precioCompra
                .multiply(BigDecimal.ONE.add(markup.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)))
                .setScale(2, RoundingMode.HALF_UP);
        this.total = precioCompra.multiply(BigDecimal.valueOf(cantidad));
    }
}
