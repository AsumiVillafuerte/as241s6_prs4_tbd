package pe.edu.vallegrande.sigrc.shopping.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicamentoInfo(
        String medicamentoId,
        String code,
        String denominacionComercial,
        String denominacionGenerica,
        String category,
        String presentacionSugerida,
        Integer minStock,
        String ubicacionSugerida,
        String laboratorioSugerido,
        BigDecimal precioCompraSugerido,
        BigDecimal precioVentaSugerido,
        LocalDate vencimientoSugerido
) {}
