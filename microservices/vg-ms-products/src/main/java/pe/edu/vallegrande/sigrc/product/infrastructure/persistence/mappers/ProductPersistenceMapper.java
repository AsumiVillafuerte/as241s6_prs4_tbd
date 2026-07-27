package pe.edu.vallegrande.sigrc.product.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product.domain.models.Product;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.entities.ProductEntity;

@Component
public class ProductPersistenceMapper {

    public Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .commercialName(entity.getCommercialName())
                .brandId(entity.getBrandId())
                .purchasePrice(entity.getPurchasePrice())
                .salePrice(entity.getSalePrice())
                .stock(entity.getStock())
                .location(entity.getLocation())
                .status(entity.getStatus() != null ? entity.getStatus().charAt(0) : 'A')
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ProductEntity toEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .commercialName(domain.getCommercialName())
                .brandId(domain.getBrandId())
                .purchasePrice(domain.getPurchasePrice())
                .salePrice(domain.getSalePrice())
                .stock(domain.getStock())
                .location(domain.getLocation())
                .status(domain.getStatus() != null ? String.valueOf(domain.getStatus()) : "A")
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
