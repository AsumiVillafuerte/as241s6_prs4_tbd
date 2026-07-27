package pe.edu.vallegrande.sigrc.product_sale.domain.ports.in;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IDeleteProductSaleUseCase {
    Mono<Void> deleteSale(UUID id);
    Mono<Void> revokeSale(UUID id);
    Mono<Void> restoreSale(UUID id);
}
