package pe.edu.vallegrade.sigrc.treatments.application.dto.response;

import lombok.*;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class TratamientoItemResponse {
    private String tratamientoMaestroId;
    private String nombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
