package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class LabKitRequest {
    @NotBlank private String name;
    private List<LabKitItemRequest> items;
    private String status;
}
