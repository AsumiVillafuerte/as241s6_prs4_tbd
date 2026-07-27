package pe.edu.vallegrande.sigrc.shopping.domain.model;

public record ProveedorInfo(
        Long supplierId,
        String businessName,
        String documentNumber,
        Boolean status
) {}
