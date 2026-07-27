package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IDecrementStockUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IProductRepository;
import reactor.core.publisher.Mono;

import pe.edu.vallegrande.sigrc.product.domain.util.PeruDateTime;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DecrementStockUseCaseImpl implements IDecrementStockUseCase {

    private final IProductRepository productRepository;

    @Override
    public Mono<Void> decrementStock(UUID productId, int quantity) {
        log.debug("Descontando {} unidades del producto {}", quantity, productId);
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Producto no encontrado con ID: " + productId)))
                .flatMap(product -> {
                    int currentStock = product.getStock() != null ? product.getStock() : 0;
                    if (currentStock < quantity) {
                        return Mono.error(new DomainException(
                                "Stock insuficiente para el producto '" + product.getCommercialName()
                                + "'. Stock actual: " + currentStock + ", cantidad solicitada: " + quantity));
                    }
                    product.setStock(currentStock - quantity);
                    product.setUpdatedAt(PeruDateTime.now());
                    return productRepository.update(product);
                })
                .doOnSuccess(p -> log.debug("Stock actualizado correctamente para producto {}", productId))
                .then();
    }
}
