package pe.edu.vallegrade.sigrc.treatments.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class TratamientoResponse {
    private String id;
    private String ticket;
    private LocalDateTime fecha;
    private String especialidadId;
    private String especialidadNombre;
    private String medicoId;
    private String medicoNombre;
    private String pacienteId;
    private String pacienteNombre;
    private String pacienteTipoDocumento; 
    private String pacienteNumero;
    private String registradoPor;
    private String tipo;
    private String metodoPago;   
    private String estado;
    private Double total;
    private List<TratamientoItemResponse> items;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
