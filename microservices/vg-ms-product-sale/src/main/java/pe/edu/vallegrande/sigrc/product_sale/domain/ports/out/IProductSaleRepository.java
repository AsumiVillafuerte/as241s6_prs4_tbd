package pe.edu.vallegrande.sigrc.product_sale.domain.ports.out;

import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSale;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSaleItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IProductSaleRepository {
    Mono<ProductSale> save(ProductSale sale);
    Mono<ProductSale> findById(UUID id);
    Flux<ProductSale> findAll();
    Flux<ProductSale> findByStatus(String status);
    Mono<ProductSale> update(ProductSale sale);
    Mono<Void> deleteById(UUID id);
    Mono<Boolean> existsById(UUID id);

    Mono<ProductSaleItem> saveItem(ProductSaleItem item);
    Flux<ProductSaleItem> findItemsBySaleId(UUID saleId);
    Mono<Void> deleteItemsBySaleId(UUID saleId);
    Mono<Long> countByDateRange(java.time.LocalDate start, java.time.LocalDate end);
    Mono<Long> countAll();
}
