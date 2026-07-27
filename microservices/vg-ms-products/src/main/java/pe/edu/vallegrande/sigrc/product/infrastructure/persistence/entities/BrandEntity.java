package pe.edu.vallegrande.sigrc.product.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("brands")
public class BrandEntity {

    @Id
    private UUID id;

    @Column("name")
    private String name;

    @Column("status")
    private String status;
}
