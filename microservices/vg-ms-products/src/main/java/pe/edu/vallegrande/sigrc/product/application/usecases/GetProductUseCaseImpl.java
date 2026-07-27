package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.application.dto.response.ProductResponse;
import pe.edu.vallegrande.sigrc.product.application.mappers.ProductMapper;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IGetProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IBrandRepository;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProductUseCaseImpl implements IGetProductUseCase {

    private final IProductRepository productRepository;
    private final IBrandRepository brandRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<ProductResponse> getProductById(UUID id) {
        log.debug("Buscando producto con ID: {}", id);
        return productRepository.findById(id)
                .doOnNext(product -> log.debug("Producto encontrado: {}", product))
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado con ID: " + id)))
                .flatMap(product -> getBrandName(product.getBrandId())
                        .map(brandName -> productMapper.toResponse(product, brandName)));
    }

    @Override
    public Flux<ProductResponse> getAllProducts() {
        log.debug("Listando todos los productos");
        return productRepository.findAll()
                .doOnNext(product -> log.debug("Producto: {}", product))
                .flatMap(product -> getBrandName(product.getBrandId())
                        .map(brandName -> productMapper.toResponse(product, brandName)))
                .doOnComplete(() -> log.debug("Listado de productos completado"));
    }

    @Override
    public Flux<ProductResponse> getProductsByStatus(Character status) {
        log.debug("Listando productos con estado: {}", status);
        return productRepository.findByStatus(status)
                .doOnNext(product -> log.debug("Producto con estado {}: {}", status, product))
                .flatMap(product -> getBrandName(product.getBrandId())
                        .map(brandName -> productMapper.toResponse(product, brandName)))
                .doOnComplete(() -> log.debug("Listado de productos por estado completado"));
    }

    private Mono<String> getBrandName(UUID brandId) {
        if (brandId == null) {
            return Mono.just("");
        }
        return brandRepository.findById(brandId)
                .map(brand -> brand.getName())
                .onErrorResume(e -> Mono.just(""));
    }
}
