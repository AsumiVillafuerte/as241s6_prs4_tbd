package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.persistence;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TratamientoItemDocument {

    private String tratamientoMaestroId;
    private String nombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}