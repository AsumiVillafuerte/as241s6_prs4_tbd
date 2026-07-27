package pe.edu.vallegrande.sigrc.product.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar una marca existente")
public class UpdateBrandRequest {

    @Size(max = 100, message = "El nombre de la marca no puede exceder 100 caracteres")
    private String name;
}
