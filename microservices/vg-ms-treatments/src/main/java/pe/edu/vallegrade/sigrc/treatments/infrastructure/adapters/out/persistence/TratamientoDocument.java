package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tratamientos")
public class TratamientoDocument {

    @Id
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

    private List<TratamientoItemDocument> items;

    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}