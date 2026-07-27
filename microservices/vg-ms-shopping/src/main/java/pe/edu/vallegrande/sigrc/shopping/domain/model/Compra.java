package pe.edu.vallegrande.sigrc.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.BusinessRuleException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    private Long id;
    private String numeroComprobante;
    private Long proveedorId;
    private String usuarioId;
    private TipoCompra tipo;
    private EstadoCompra estado;
    private BigDecimal precioCompraTotal;
    private BigDecimal precioVentaTotal;
    private LocalDateTime fechaCompra;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<DetalleCompraMedicamento> detalles;

    // Campos de enriquecimiento: no persisten en BD, se rellenan por los casos de uso Get/List
    private UsuarioInfo usuarioInfo;
    private ProveedorInfo proveedorInfo;

    public void calcularTotales() {
        this.precioCompraTotal = detalles.stream()
                .map(d -> d.getPrecioCompra().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.precioVentaTotal = detalles.stream()
                .map(d -> d.getPrecioVenta().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void revocar() {
        if (this.estado == EstadoCompra.REVOCADO) {
            throw new BusinessRuleException("La compra ya se encuentra revocada");
        }
        this.estado = EstadoCompra.REVOCADO;
    }
}
