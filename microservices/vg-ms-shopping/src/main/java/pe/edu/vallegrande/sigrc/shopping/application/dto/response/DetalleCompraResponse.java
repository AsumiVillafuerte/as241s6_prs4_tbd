package pe.edu.vallegrande.sigrc.shopping.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DetalleCompraResponse(
        Long id,
        String medicamentoId,
        String denominacionComercial,
        String denominacionGenerica,
        String laboratorio,
        String presentacion,
        String lote,
        LocalDate vencimiento,
        String ubicacion,
        BigDecimal precioCompra,
        BigDecimal precioVenta,
        Integer cantidad,
        Integer minStock,
        BigDecimal total,
        BigDecimal subtotalVenta,
        BigDecimal markupPorcentaje,
        String loteInventarioId,
        LocalDateTime createdAt
) {}
