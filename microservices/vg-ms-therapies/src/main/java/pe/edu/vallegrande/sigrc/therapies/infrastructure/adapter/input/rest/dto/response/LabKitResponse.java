package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class LabKitResponse {
    private String id;
    private String name;
    private List<LabKitItemResponse> items;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
