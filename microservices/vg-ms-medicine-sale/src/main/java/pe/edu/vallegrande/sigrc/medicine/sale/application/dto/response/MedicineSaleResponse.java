package pe.edu.vallegrande.sigrc.medicine.sale.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicineSaleResponse {

    private String id;
    private String ticket;
    private LocalDateTime saleDate;
    private String patientId;
    private String patientName;
    private String dni;
    private String cashierId;
    private String cashierName;
    private BigDecimal total;
    private SaleType type;
    private SaleStatus status;
    private List<SaleItemResponse> items;
}
