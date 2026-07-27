package pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSale;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSaleItem;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductSaleRepository;
import pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.mappers.SalePersistenceMapper;
import pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.repositories.SaleItemR2dbcRepository;
import pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.repositories.SaleR2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductSaleRepositoryImpl implements IProductSaleRepository {

    private final SaleR2dbcRepository saleR2dbcRepository;
    private final SaleItemR2dbcRepository saleItemR2dbcRepository;
    private final SalePersistenceMapper mapper;

    @Override
    public Mono<ProductSale> save(ProductSale sale) {
        return saleR2dbcRepository.save(mapper.toEntity(sale))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ProductSale> findById(UUID id) {
        return saleR2dbcRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ProductSale> findAll() {
        return saleR2dbcRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ProductSale> findByStatus(String status) {
        return saleR2dbcRepository.findByStatus(status)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ProductSale> update(ProductSale sale) {
        return save(sale);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return saleR2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return saleR2dbcRepository.existsById(id);
    }

    @Override
    public Mono<ProductSaleItem> saveItem(ProductSaleItem item) {
        return saleItemR2dbcRepository.save(mapper.itemToEntity(item))
                .map(mapper::itemToDomain);
    }

    @Override
    public Flux<ProductSaleItem> findItemsBySaleId(UUID saleId) {
        return saleItemR2dbcRepository.findBySaleId(saleId)
                .map(mapper::itemToDomain);
    }

    @Override
    public Mono<Void> deleteItemsBySaleId(UUID saleId) {
        return saleItemR2dbcRepository.deleteBySaleId(saleId);
    }

    @Override
    public Mono<Long> countByDateRange(java.time.LocalDate start, java.time.LocalDate end) {
        return saleR2dbcRepository.countByDateRange(start, end);
    }

    @Override
    public Mono<Long> countAll() {
        return saleR2dbcRepository.countAll();
    }
}
