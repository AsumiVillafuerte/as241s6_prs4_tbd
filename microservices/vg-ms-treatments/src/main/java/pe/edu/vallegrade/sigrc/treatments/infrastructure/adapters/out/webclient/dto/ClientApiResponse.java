package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ClientApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
