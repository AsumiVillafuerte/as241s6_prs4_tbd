package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper;

import pe.edu.vallegrande.sigrc.therapies.domain.model.*;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.*;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class WebMapper {

    // Therapy
    public Therapy toDomain(TherapyRequest request) {
        return Therapy.builder()
            .specialtyId(request.getSpecialtyId())
            .clientType(request.getClientType())
            .medic(request.getMedic())
            .medicName(request.getMedicName())
            .patient(request.getPatient())
            .patientName(request.getPatientName())
            .dni(request.getDni())
            .registerBy(request.getRegisterBy())
            .registerByName(request.getRegisterByName())
            .total(request.getTotal())
            .type(request.getType())
            .tarjeta(request.getTarjeta())
            .build();
    }

    public TherapyResponse toResponse(Therapy domain) {
        return TherapyResponse.builder()
            .id(domain.getId())
            .ticket(domain.getTicket())
            .specialtyId(domain.getSpecialtyId())
            .clientType(domain.getClientType())
            .medic(domain.getMedic())
            .medicName(domain.getMedicName())
            .patient(domain.getPatient())
            .patientName(domain.getPatientName())
            .dni(domain.getDni())
            .registerBy(domain.getRegisterBy())
            .registerByName(domain.getRegisterByName())
            .total(domain.getTotal())
            .type(domain.getType())
            .tarjeta(domain.getTarjeta())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    // TherapyPrice
    public TherapyPrice toDomain(TherapyPriceRequest request) {
        return TherapyPrice.builder()
            .specialtyId(request.getSpecialtyId())
            .clientType(request.getClientType())
            .price(request.getPrice())
            .status(request.getStatus())
            .build();
    }

    public TherapyPriceResponse toResponse(TherapyPrice domain) {
        return TherapyPriceResponse.builder()
            .id(domain.getId())
            .specialtyId(domain.getSpecialtyId())
            .clientType(domain.getClientType())
            .price(domain.getPrice())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    // Treatment
    public Treatment toDomain(TreatmentRequest request) {
        return Treatment.builder()
            .code(request.getCode())
            .name(request.getName())
            .specialtyId(request.getSpecialtyId())
            .salePrice(request.getSalePrice())
            .status(request.getStatus())
            .build();
    }

    public TreatmentResponse toResponse(Treatment domain) {
        return TreatmentResponse.builder()
            .id(domain.getId())
            .code(domain.getCode())
            .name(domain.getName())
            .specialtyId(domain.getSpecialtyId())
            .salePrice(domain.getSalePrice())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    // LabTest
    public LabTest toDomain(LabTestRequest request) {
        return LabTest.builder()
            .name(request.getName())
            .price(request.getPrice())
            .status(request.getStatus())
            .build();
    }

    public LabTestResponse toResponse(LabTest domain) {
        return LabTestResponse.builder()
            .id(domain.getId())
            .name(domain.getName())
            .price(domain.getPrice())
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    // LabKit
    public LabKit toDomain(LabKitRequest request) {
        return LabKit.builder()
            .name(request.getName())
            .items(request.getItems() != null
                ? request.getItems().stream().map(this::toItemDomain).collect(Collectors.toList())
                : null)
            .status(request.getStatus())
            .build();
    }

    private LabKitItem toItemDomain(LabKitItemRequest item) {
        return LabKitItem.builder()
            .labTestId(item.getLabTestId())
            .prueba(item.getPrueba())
            .labTestName(item.getLabTestName())
            .labTestPrice(item.getLabTestPrice())
            .build();
    }

    public LabKitResponse toResponse(LabKit domain) {
        return LabKitResponse.builder()
            .id(domain.getId())
            .name(domain.getName())
            .items(domain.getItems() != null
                ? domain.getItems().stream().map(this::toItemResponse).collect(Collectors.toList())
                : null)
            .status(domain.getStatus())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    private LabKitItemResponse toItemResponse(LabKitItem item) {
        return LabKitItemResponse.builder()
            .labTestId(item.getLabTestId())
            .prueba(item.getPrueba())
            .labTestName(item.getLabTestName())
            .labTestPrice(item.getLabTestPrice())
            .build();
    }
}
