package pe.edu.vallegrande.sigrc.suppliers.application.dto.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private boolean success;
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
