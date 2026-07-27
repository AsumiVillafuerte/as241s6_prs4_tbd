package pe.edu.vallegrande.sigrc.product.application.mappers;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product.application.dto.request.CreateProductRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.request.UpdateProductRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.ProductResponse;
import pe.edu.vallegrande.sigrc.product.domain.models.Product;

import pe.edu.vallegrande.sigrc.product.domain.util.PeruDateTime;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    public Product toDomain(CreateProductRequest request) {
        return Product.builder()
                .commercialName(request.getCommercialName())
                .brandId(request.getBrandId())
                .purchasePrice(request.getPurchasePrice())
                .salePrice(request.getSalePrice())
                .stock(request.getStock() != null ? request.getStock() : 0)
                .location(request.getLocation())
                .status('A')
                .createdAt(PeruDateTime.now())
                .updatedAt(PeruDateTime.now())
                .build();
    }

    public Product mapUpdateToDomain(UpdateProductRequest request) {
        return Product.builder()
                .commercialName(request.getCommercialName())
                .brandId(request.getBrandId())
                .purchasePrice(request.getPurchasePrice())
                .salePrice(request.getSalePrice())
                .stock(request.getStock())
                .location(request.getLocation())
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return toResponse(product, null);
    }

    public ProductResponse toResponse(Product product, String brandName) {
        return ProductResponse.builder()
                .id(product.getId())
                .commercialName(product.getCommercialName())
                .brandId(product.getBrandId())
                .brandName(brandName)
                .purchasePrice(product.getPurchasePrice())
                .salePrice(product.getSalePrice())
                .stock(product.getStock())
                .location(product.getLocation())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
