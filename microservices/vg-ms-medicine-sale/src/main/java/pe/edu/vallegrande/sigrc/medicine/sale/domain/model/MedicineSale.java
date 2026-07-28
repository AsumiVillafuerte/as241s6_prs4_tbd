package pe.edu.vallegrande.sigrc.medicine.sale.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineSale {

    private String id;

    /** Número único de venta. Ej: FAR-2026-06-10-018781 */
    private String ticket;

    private LocalDateTime saleDate;

    // Datos del paciente
    private String patientId;
    private String patientName;
    private String dni;

    // Datos del cajero
    private String cashierId;
    private String cashierName;

    private BigDecimal total;
    private SaleType type;
    private SaleStatus status;
    private List<SaleItem> items;
}
