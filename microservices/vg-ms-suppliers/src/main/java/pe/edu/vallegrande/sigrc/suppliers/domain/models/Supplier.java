package pe.edu.vallegrande.sigrc.suppliers.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    private Long supplierId;
    private String businessName;
    private String documentType;
    private String documentNumber;
    private String address;
    private String phone;
    private String email;
    private String imageUrl;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void initializeForCreation() {
        this.status = true;
        this.createdAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void restore() {
        this.status = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
}
