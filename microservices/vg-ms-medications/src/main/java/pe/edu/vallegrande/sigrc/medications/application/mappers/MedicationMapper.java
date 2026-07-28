package pe.edu.vallegrande.sigrc.medications.application.mappers;

import pe.edu.vallegrande.sigrc.medications.application.dto.request.CreateMedicationRequest;
import pe.edu.vallegrande.sigrc.medications.application.dto.request.UpdateMedicationRequest;
import pe.edu.vallegrande.sigrc.medications.application.dto.response.MedicationResponse;
import pe.edu.vallegrande.sigrc.medications.domain.model.Medication;

public class MedicationMapper {

    public static Medication toDomain(CreateMedicationRequest request) {
        return Medication.builder()
                .code(request.getCode())
                .genericName(request.getGenericName())
                .commercialName(request.getCommercialName())
                .category(request.getCategory())
                .form(request.getForm())
                .stock(request.getStock())
                .minStock(request.getMinStock() != null ? request.getMinStock() : 10)
                .description(request.getDescription())
                .sideEffects(request.getSideEffects())
                .requiresPrescription(request.getRequiresPrescription() != null ? request.getRequiresPrescription() : false)
                .supplierId(request.getSupplierId())
                .location(request.getLocation())
                .laboratory(request.getLaboratory())
                .costPrice(request.getCostPrice())
                .salePrice(request.getSalePrice())
                .expirationDate(request.getExpirationDate())
                .batchNumber(request.getBatchNumber())
                .status("active")
                .build();
    }

    public static Medication toDomain(UpdateMedicationRequest request) {
        return Medication.builder()
                .genericName(request.getGenericName())
                .commercialName(request.getCommercialName())
                .category(request.getCategory())
                .form(request.getForm())
                .stock(request.getStock())
                .minStock(request.getMinStock())
                .description(request.getDescription())
                .sideEffects(request.getSideEffects())
                .requiresPrescription(request.getRequiresPrescription())
                .supplierId(request.getSupplierId())
                .location(request.getLocation())
                .laboratory(request.getLaboratory())
                .costPrice(request.getCostPrice())
                .salePrice(request.getSalePrice())
                .expirationDate(request.getExpirationDate())
                .batchNumber(request.getBatchNumber())
                .build();
    }

    public static MedicationResponse toResponse(Medication medication) {
        return MedicationResponse.builder()
                .id(medication.getId())
                .code(medication.getCode())
                .genericName(medication.getGenericName())
                .commercialName(medication.getCommercialName())
                .category(medication.getCategory())
                .form(medication.getForm())
                .stock(medication.getStock())
                .minStock(medication.getMinStock())
                .description(medication.getDescription())
                .sideEffects(medication.getSideEffects())
                .requiresPrescription(medication.getRequiresPrescription())
                .supplierId(medication.getSupplierId())
                .location(medication.getLocation())
                .laboratory(medication.getLaboratory())
                .costPrice(medication.getCostPrice())
                .salePrice(medication.getSalePrice())
                .expirationDate(medication.getExpirationDate())
                .batchNumber(medication.getBatchNumber())
                .status(medication.getStatus())
                .build();
    }
}
