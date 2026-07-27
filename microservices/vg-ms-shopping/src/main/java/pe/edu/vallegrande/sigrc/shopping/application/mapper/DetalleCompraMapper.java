package pe.edu.vallegrande.sigrc.shopping.application.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.shopping.application.dto.request.DetalleCompraRequest;
import pe.edu.vallegrande.sigrc.shopping.application.dto.response.DetalleCompraResponse;
import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;

import java.math.BigDecimal;

@Component
public class DetalleCompraMapper {

    private static final BigDecimal MARKUP_DEFAULT = new BigDecimal("30.00");

    public DetalleCompraMedicamento toDomain(DetalleCompraRequest request) {
        return DetalleCompraMedicamento.builder()
                .medicamentoId(request.medicamentoId())
                .laboratorio(request.laboratorio())
                .presentacion(request.presentacion())
                .lote(request.lote())
                .vencimiento(request.vencimiento())
                .ubicacion(request.ubicacion())
                .precioCompra(request.precioCompra())
                .markupPorcentaje(request.markupPorcentaje() != null
                        ? request.markupPorcentaje()
                        : MARKUP_DEFAULT)
                .cantidad(request.cantidad())
                .minStock(request.minStock())
                .build();
    }

    public DetalleCompraResponse toResponse(DetalleCompraMedicamento detalle) {
        BigDecimal subtotalVenta = detalle.getPrecioVenta() != null && detalle.getCantidad() != null
                ? detalle.getPrecioVenta().multiply(BigDecimal.valueOf(detalle.getCantidad()))
                : BigDecimal.ZERO;

        return new DetalleCompraResponse(
                detalle.getId(),
                detalle.getMedicamentoId(),
                detalle.getDenominacionComercial(),
                detalle.getDenominacionGenerica(),
                detalle.getLaboratorio(),
                detalle.getPresentacion(),
                detalle.getLote(),
                detalle.getVencimiento(),
                detalle.getUbicacion(),
                detalle.getPrecioCompra(),
                detalle.getPrecioVenta(),
                detalle.getCantidad(),
                detalle.getMinStock(),
                detalle.getTotal(),
                subtotalVenta,
                detalle.getMarkupPorcentaje(),
                detalle.getLoteInventarioId(),
                detalle.getCreatedAt()
        );
    }
}
