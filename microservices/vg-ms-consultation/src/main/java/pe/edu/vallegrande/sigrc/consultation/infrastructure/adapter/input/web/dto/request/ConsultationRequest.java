package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ConsultationRequest(
    @NotBlank String specialtyId,
    @NotBlank String medic,
    @NotBlank String medicName,
    @NotBlank String patient,
    @NotBlank String patientName,
    @NotBlank String dni,
    @NotBlank String registerBy,
    @NotBlank String registerByName,
    @NotNull @Positive BigDecimal total,
    @NotBlank String tipo,
    String tarjeta
) {}
