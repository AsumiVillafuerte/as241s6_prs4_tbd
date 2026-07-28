package pe.edu.vallegrande.sigrc.medicine.sale.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineSaleRequest {

    @NotBlank(message = "El ID del paciente es requerido")
    private String patientId;

    @NotBlank(message = "El ID del cajero es requerido")
    private String cashierId;

    @NotNull(message = "El tipo de venta es requerido")
    private SaleType type;

    // Si no se envía, por defecto es VENDIDO. Valores válidos: VENDIDO, DONADO, CONSIGNADO
    private SaleStatus status;

    @NotEmpty(message = "Debe incluir al menos un medicamento")
    @Valid
    private List<SaleItemRequest> items;
}
