package pe.edu.vallegrande.sigrc.shopping.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompraListItemResponse(
        Long id,
        String numeroComprobante,
        String proveedorNombre,
        String registradoPor,
        String tipo,
        String estado,
        BigDecimal precioCompraTotal,
        BigDecimal precioVentaTotal,
        LocalDateTime fechaCompra
) {}
