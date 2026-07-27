package pe.edu.vallegrande.sigrc.suppliers.application.mappers;

import pe.edu.vallegrande.sigrc.suppliers.application.dto.request.CreateSupplierRequest;
import pe.edu.vallegrande.sigrc.suppliers.application.dto.request.UpdateSupplierRequest;
import pe.edu.vallegrande.sigrc.suppliers.application.dto.response.SupplierResponse;
import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;

public class SupplierMapper {

    private SupplierMapper() {}

    public static Supplier toDomain(CreateSupplierRequest request) {
        return Supplier.builder()
                .businessName(request.getBusinessName())
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public static Supplier toDomain(UpdateSupplierRequest request) {
        return Supplier.builder()
                .businessName(request.getBusinessName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .imageUrl(request.getImageUrl())
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .build();
    }

    public static SupplierResponse toResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .supplierId(supplier.getSupplierId())
                .businessName(supplier.getBusinessName())
                .documentType(supplier.getDocumentType())
                .documentNumber(supplier.getDocumentNumber())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .imageUrl(supplier.getImageUrl())
                .status(supplier.getStatus())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }
}
