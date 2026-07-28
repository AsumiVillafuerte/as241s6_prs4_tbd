package pe.edu.vallegrade.sigrc.treatments.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTipoRequest {
    @NotBlank(message = "El tipo es requerido")
    private String tipo;  // "venta" | "donado"
}
