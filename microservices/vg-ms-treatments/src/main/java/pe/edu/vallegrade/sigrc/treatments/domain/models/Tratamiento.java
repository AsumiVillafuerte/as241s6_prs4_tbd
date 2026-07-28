package pe.edu.vallegrade.sigrc.treatments.domain.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Tratamiento {

    private String id;
    private String ticket;
    private LocalDateTime fecha;

    // Snapshots del microservicio maestro
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
    private List<TratamientoItem> items;

    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}