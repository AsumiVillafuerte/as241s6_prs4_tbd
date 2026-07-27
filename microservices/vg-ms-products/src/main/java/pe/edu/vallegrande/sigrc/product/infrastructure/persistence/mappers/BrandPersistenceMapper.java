package pe.edu.vallegrande.sigrc.product.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product.domain.models.Brand;
import pe.edu.vallegrande.sigrc.product.infrastructure.persistence.entities.BrandEntity;

@Component
public class BrandPersistenceMapper {

    public Brand toDomain(BrandEntity entity) {
        return Brand.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus() != null ? entity.getStatus().charAt(0) : 'A')
                .build();
    }

    public BrandEntity toEntity(Brand domain) {
        return BrandEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .status(domain.getStatus() != null ? String.valueOf(domain.getStatus()) : "A")
                .build();
    }
}
