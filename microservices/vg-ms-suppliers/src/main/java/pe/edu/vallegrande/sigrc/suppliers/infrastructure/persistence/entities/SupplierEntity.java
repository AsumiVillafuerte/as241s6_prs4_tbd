package pe.edu.vallegrande.sigrc.suppliers.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("supplier")
public class SupplierEntity {

    @Id
    @Column("supplier_id")
    private Long supplierId;

    @Column("business_name")
    private String businessName;

    @Column("document_type")
    private String documentType;

    @Column("document_number")
    private String documentNumber;

    private String address;
    private String phone;
    private String email;

    @Column("image_url")
    private String imageUrl;

    private Boolean status;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
