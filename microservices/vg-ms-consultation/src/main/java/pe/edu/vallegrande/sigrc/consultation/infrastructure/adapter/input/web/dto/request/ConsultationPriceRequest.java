package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ConsultationPriceRequest(
    @NotBlank String specialtyId,
    @NotNull @Positive BigDecimal price
) {}
