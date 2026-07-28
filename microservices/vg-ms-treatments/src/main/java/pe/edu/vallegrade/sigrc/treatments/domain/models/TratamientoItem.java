package pe.edu.vallegrade.sigrc.treatments.domain.models;

import lombok.*;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class TratamientoItem {

    private String tratamientoMaestroId;
    private String nombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
