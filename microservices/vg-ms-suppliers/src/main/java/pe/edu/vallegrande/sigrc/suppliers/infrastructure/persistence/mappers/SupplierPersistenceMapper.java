package pe.edu.vallegrande.sigrc.suppliers.infrastructure.persistence.mappers;

import pe.edu.vallegrande.sigrc.suppliers.domain.models.Supplier;
import pe.edu.vallegrande.sigrc.suppliers.infrastructure.persistence.entities.SupplierEntity;

public class SupplierPersistenceMapper {

    private SupplierPersistenceMapper() {}

    public static Supplier toDomain(SupplierEntity entity) {
        return Supplier.builder()
                .supplierId(entity.getSupplierId())
                .businessName(entity.getBusinessName())
                .documentType(entity.getDocumentType())
                .documentNumber(entity.getDocumentNumber())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .imageUrl(entity.getImageUrl())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static SupplierEntity toEntity(Supplier supplier) {
        return SupplierEntity.builder()
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
