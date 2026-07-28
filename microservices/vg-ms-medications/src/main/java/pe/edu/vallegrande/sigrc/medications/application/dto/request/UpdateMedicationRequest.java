package pe.edu.vallegrande.sigrc.medications.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateMedicationRequest {
    private String genericName;
    private String commercialName;
    private String category;
    private String form;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
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
}
