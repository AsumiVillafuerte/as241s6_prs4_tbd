package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IDeleteProductUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IProductRepository;
import reactor.core.publisher.Mono;

import pe.edu.vallegrande.sigrc.product.domain.util.PeruDateTime;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCaseImpl implements IDeleteProductUseCase {

    private final IProductRepository productRepository;

    @Override
    public Mono<Void> deleteProduct(UUID id) {
        return productRepository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new NotFoundException("Producto no encontrado con ID: " + id));
                    }
                    return productRepository.deleteById(id);
                });
    }

    @Override
    public Mono<Void> logicalDeleteProduct(UUID id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado con ID: " + id)))
                .flatMap(product -> {
                    product.setStatus('I');
                    product.setUpdatedAt(PeruDateTime.now());
                    return productRepository.update(product);
                })
                .then();
    }
}
