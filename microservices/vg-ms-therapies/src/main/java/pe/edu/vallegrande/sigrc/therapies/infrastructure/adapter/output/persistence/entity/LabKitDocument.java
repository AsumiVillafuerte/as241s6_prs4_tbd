package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "lab_kits")
public class LabKitDocument {
    @Id private String id;
    private String name;
    private List<LabKitItemDocument> items;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
