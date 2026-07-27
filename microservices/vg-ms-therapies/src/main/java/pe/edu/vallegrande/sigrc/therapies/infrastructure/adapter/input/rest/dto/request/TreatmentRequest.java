package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TreatmentRequest {
    private String code;
    @NotBlank private String name;
    @NotBlank private String specialtyId;
    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal salePrice;
    private String status;
}