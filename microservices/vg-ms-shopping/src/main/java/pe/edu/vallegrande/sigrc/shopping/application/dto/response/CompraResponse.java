package pe.edu.vallegrande.sigrc.shopping.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CompraResponse(
        Long id,
        String numeroComprobante,
        ProveedorDTO proveedor,
        UsuarioDTO usuario,
        String tipo,
        String estado,
        BigDecimal precioCompraTotal,
        BigDecimal precioVentaTotal,
        LocalDateTime fechaCompra,
        LocalDateTime createdAt,
        List<DetalleCompraResponse> detalles
) {}
