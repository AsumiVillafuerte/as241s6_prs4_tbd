package pe.edu.vallegrande.sigrc.suppliers.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SupplierResponse {

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
}
