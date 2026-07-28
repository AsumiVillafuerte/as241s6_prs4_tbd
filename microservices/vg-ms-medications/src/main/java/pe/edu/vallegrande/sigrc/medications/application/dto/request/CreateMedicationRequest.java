package pe.edu.vallegrande.sigrc.medications.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMedicationRequest {

    @NotBlank(message = "El código es obligatorio")
    private String code;

    @NotBlank(message = "El nombre genérico es obligatorio")
    private String genericName;

    private String commercialName;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @NotBlank(message = "La forma farmacéutica es obligatoria")
    private String form;

    @NotNull(message = "El stock es obligatorio")
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
