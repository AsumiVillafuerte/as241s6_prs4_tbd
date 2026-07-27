package pe.edu.vallegrande.sigrc.nombremicro.infrastructure.adapters.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("entities")  // Nombre de la tabla en PostgreSQL
public class EntityDocument {

    @Id
    private Long id;
    
    private String name;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Agregar más campos según tu entidad
}


