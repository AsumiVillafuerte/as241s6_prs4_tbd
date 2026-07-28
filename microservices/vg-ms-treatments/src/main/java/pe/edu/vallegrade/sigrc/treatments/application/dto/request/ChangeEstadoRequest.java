package pe.edu.vallegrade.sigrc.treatments.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor 
@AllArgsConstructor
@Builder
@Data
public class ChangeEstadoRequest {

    @NotBlank(message = "El estado es requerido")
    private String estado;  // "consignado" | "revocado" | "donado"
}
