package pe.edu.vallegrande.sigrc.product.application.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta estándar de la API")
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
}
