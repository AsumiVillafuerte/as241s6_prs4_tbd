package pe.edu.vallegrande.sigrc.medications.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicationResponse {
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
}
