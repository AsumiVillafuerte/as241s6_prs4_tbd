package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IRestoreProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IProductRepository;
import reactor.core.publisher.Mono;

import pe.edu.vallegrande.sigrc.product.domain.util.PeruDateTime;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestoreProductUseCaseImpl implements IRestoreProductUseCase {

    private final IProductRepository productRepository;

    @Override
    public Mono<Void> restoreProduct(UUID id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado con ID: " + id)))
                .flatMap(product -> {
                    product.setStatus('A');
                    product.setUpdatedAt(PeruDateTime.now());
                    return productRepository.update(product);
                })
                .then();
    }
}
