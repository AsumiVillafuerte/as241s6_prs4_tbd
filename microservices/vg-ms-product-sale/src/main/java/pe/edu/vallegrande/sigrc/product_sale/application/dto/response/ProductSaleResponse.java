package pe.edu.vallegrande.sigrc.product_sale.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSaleResponse {

    private UUID id;
    private String ticketNumber;
    private LocalDate saleDate;

    // Datos del paciente (aplanados desde ms-patients)
    private String patientName;
    private String dni;

    // Datos del usuario (aplanados desde ms-users)
    private String registeredBy;

    private BigDecimal total;
    private String saleType;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ProductItemResponse> items;
}
