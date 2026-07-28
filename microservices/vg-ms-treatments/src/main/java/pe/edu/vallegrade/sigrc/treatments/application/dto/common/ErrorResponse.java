package pe.edu.vallegrade.sigrc.treatments.application.dto.common;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
