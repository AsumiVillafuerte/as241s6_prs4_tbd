package pe.edu.vallegrande.sigrc.product_sale.domain.ports.in;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IUpdateSaleTypeUseCase {
    Mono<Void> updateSaleType(UUID id, String saleType);
}
