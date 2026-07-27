package pe.edu.vallegrande.sigrc.product.domain.ports.in;

import reactor.core.publisher.Mono;
import java.util.UUID;

public interface IDecrementStockUseCase {
    /**
     * Descuenta la cantidad indicada del stock del producto.
     * Lanza DomainException si el stock resultante sería negativo.
     */
    Mono<Void> decrementStock(UUID productId, int quantity);
}
