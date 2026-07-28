package pe.edu.vallegrande.sigrc.medications.infrastructure.adapters.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "medications")
public class MedicationDocument {

    @Id
    private String id;

    private String code;
    private String genericName;
    private String commercialName;
    private String category;
    private String form;
    private Integer stock;
    private Integer minStock;
    private String description;
    private String sideEffects;
    private Boolean requiresPrescription;
    private String supplierId;
    private String location;
    private String laboratory;
    private Double costPrice;
    private Double salePrice;
    private String expirationDate;
    private String batchNumber;
    private String status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
