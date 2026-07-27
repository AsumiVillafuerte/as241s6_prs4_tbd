package pe.edu.vallegrande.sigrc.product.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear una nueva marca")
public class CreateBrandRequest {

    @NotBlank(message = "El nombre de la marca es obligatorio")
    @Size(max = 100, message = "El nombre de la marca no puede exceder 100 caracteres")
    private String name;
}
