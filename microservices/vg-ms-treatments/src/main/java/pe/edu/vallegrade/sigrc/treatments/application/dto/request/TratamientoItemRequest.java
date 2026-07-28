package pe.edu.vallegrade.sigrc.treatments.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TratamientoItemRequest {

    @NotBlank(message = "El id del tratamiento maestro es requerido")
    private String tratamientoMaestroId;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Max(value = 1, message = "La cantidad máxima es 1")
    private Integer cantidad;
}