package pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSale;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSaleItem;
import pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.entities.SaleEntity;
import pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.entities.SaleItemEntity;

@Component
public class SalePersistenceMapper {

    public ProductSale toDomain(SaleEntity entity) {
        return ProductSale.builder()
                .id(entity.getId())
                .ticketNumber(entity.getTicketNumber())
                .patientId(entity.getPatientId())
                .userId(entity.getUserId())
                .total(entity.getTotal())
                .saleType(entity.getSaleType())
                .status(entity.getStatus())
                .saleDate(entity.getSaleDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public SaleEntity toEntity(ProductSale domain) {
        return SaleEntity.builder()
                .id(domain.getId())
                .ticketNumber(domain.getTicketNumber())
                .patientId(domain.getPatientId())
                .userId(domain.getUserId())
                .total(domain.getTotal())
                .saleType(domain.getSaleType())
                .status(domain.getStatus())
                .saleDate(domain.getSaleDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public ProductSaleItem itemToDomain(SaleItemEntity entity) {
        return ProductSaleItem.builder()
                .id(entity.getId())
                .saleId(entity.getSaleId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .subtotal(entity.getSubtotal())
                .build();
    }

    public SaleItemEntity itemToEntity(ProductSaleItem domain) {
        return SaleItemEntity.builder()
                .id(domain.getId())
                .saleId(domain.getSaleId())
                .productId(domain.getProductId())
                .quantity(domain.getQuantity())
                .unitPrice(domain.getUnitPrice())
                // subtotal es columna generada, no se envía al insertar
                .build();
    }
}
