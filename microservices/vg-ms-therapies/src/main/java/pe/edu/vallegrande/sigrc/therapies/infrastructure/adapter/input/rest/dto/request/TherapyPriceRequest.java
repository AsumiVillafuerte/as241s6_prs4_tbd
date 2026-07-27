package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TherapyPriceRequest {
    @NotBlank private String specialtyId;
    @NotBlank private String clientType;
    @NotNull @Positive private BigDecimal price;
    private String status;
}
