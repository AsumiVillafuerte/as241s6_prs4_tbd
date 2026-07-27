package pe.edu.vallegrande.sigrc.product.application.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import pe.edu.vallegrande.sigrc.product.domain.util.PeruDateTime;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de error de la API")
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> errors;

    public ErrorResponse(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = PeruDateTime.now();
        this.errors = errors;
    }
}
