package pe.edu.vallegrande.sigrc.product.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private UUID id;
    private String name;
    private Character status;
}
