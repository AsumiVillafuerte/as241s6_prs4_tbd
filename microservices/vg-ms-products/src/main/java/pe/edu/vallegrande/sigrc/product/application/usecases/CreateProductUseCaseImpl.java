package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.application.dto.request.CreateProductRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.ProductResponse;
import pe.edu.vallegrande.sigrc.product.application.mappers.ProductMapper;
import pe.edu.vallegrande.sigrc.product.domain.models.Product;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.ICreateProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IProductRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements ICreateProductUseCase {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<ProductResponse> createProduct(CreateProductRequest request) {
        log.debug("Creando producto: {}", request);
        Product product = productMapper.toDomain(request);
        return productRepository.save(product)
                .doOnNext(saved -> log.debug("Producto creado con ID: {}", saved.getId()))
                .map(productMapper::toResponse);
    }
}
