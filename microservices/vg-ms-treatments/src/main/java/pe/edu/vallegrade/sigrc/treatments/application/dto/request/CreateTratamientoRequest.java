package pe.edu.vallegrade.sigrc.treatments.application.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor 
@AllArgsConstructor
@Data
public class CreateTratamientoRequest {

    @NotBlank(message = "La especialidad es requerida")
    private String especialidadId;

    
    private String especialidadNombre;

    @NotBlank(message = "El médico es requerido")
    private String medicoId;

    
    private String medicoNombre;

    @NotBlank(message = "El paciente es requerido")
    private String pacienteId;



    private String pacienteNombre;

    private String pacienteTipoDocumento;

    private String pacienteNumero;

    @NotBlank(message = "El registrado por es requerido")
    private String registradoPor;

    @NotBlank(message = "El tipo es requerido")
    private String tipo;       

    private String metodoPago;  

    @NotEmpty(message = "Debe incluir al menos un ítem")
    @jakarta.validation.Valid
    private List<TratamientoItemRequest> items;
}