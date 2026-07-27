package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.output.repository.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client_types")
public class ClientTypeDocument {
    @Id 
    private String id;
    private String name;
    private String description;
    private String status;
    
    @CreatedDate 
    private LocalDateTime createdAt;
    
    @LastModifiedDate 
    private LocalDateTime updatedAt;
}
