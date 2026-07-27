package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TherapyRequest {
    @NotBlank private String specialtyId;
    @NotBlank private String clientType;
    @NotBlank private String medic;
    @NotBlank private String medicName;
    @NotBlank private String patient;
    @NotBlank private String patientName;
    @NotBlank private String dni;
    @NotBlank private String registerBy;
    @NotBlank private String registerByName;
    @NotNull @Positive private BigDecimal total;
    @NotBlank private String type;
    private String tarjeta;
}
