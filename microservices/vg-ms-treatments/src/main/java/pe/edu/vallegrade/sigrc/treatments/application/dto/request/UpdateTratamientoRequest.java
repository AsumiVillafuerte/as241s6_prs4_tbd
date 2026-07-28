package pe.edu.vallegrade.sigrc.treatments.application.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class UpdateTratamientoRequest {

    private String medicoId;
    private String medicoNombre;
    private String tipo;
    private String metodoPago;   

    @NotEmpty(message = "Debe incluir al menos un ítem")
    @jakarta.validation.Valid
    private List<TratamientoItemRequest> items;
}
