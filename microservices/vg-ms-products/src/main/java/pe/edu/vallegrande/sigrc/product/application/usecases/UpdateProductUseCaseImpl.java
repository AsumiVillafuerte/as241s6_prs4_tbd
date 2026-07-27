package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.application.dto.request.UpdateProductRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.ProductResponse;
import pe.edu.vallegrande.sigrc.product.application.mappers.ProductMapper;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.models.Product;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IUpdateProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IProductRepository;
import reactor.core.publisher.Mono;

import pe.edu.vallegrande.sigrc.product.domain.util.PeruDateTime;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements IUpdateProductUseCase {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<ProductResponse> updateProduct(UUID id, UpdateProductRequest request) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado con ID: " + id)))
                .map(existing -> {
                    Product updated = productMapper.mapUpdateToDomain(request);
                    // Mantener campos que no se actualizan
                    updated.setId(existing.getId());
                    updated.setStatus(existing.getStatus());
                    updated.setCreatedAt(existing.getCreatedAt());
                    updated.setUpdatedAt(PeruDateTime.now());
                    // Actualizar solo campos no nulos del request
                    if (updated.getCommercialName() == null) updated.setCommercialName(existing.getCommercialName());
                    if (updated.getBrandId() == null) updated.setBrandId(existing.getBrandId());
                    if (updated.getPurchasePrice() == null) updated.setPurchasePrice(existing.getPurchasePrice());
                    if (updated.getSalePrice() == null) updated.setSalePrice(existing.getSalePrice());
                    if (updated.getStock() == null) updated.setStock(existing.getStock());
                    if (updated.getLocation() == null) updated.setLocation(existing.getLocation());
                    return updated;
                })
                .flatMap(productRepository::update)
                .map(productMapper::toResponse);
    }
}
