package pe.edu.vallegrande.sigrc.product_sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product_sale.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.IDeleteProductSaleUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductSaleRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteProductSaleUseCaseImpl implements IDeleteProductSaleUseCase {

    private final IProductSaleRepository saleRepository;

    @Override
    public Mono<Void> deleteSale(UUID id) {
        log.debug("Eliminando venta con ID: {}", id);
        return saleRepository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new NotFoundException("Venta no encontrada con ID: " + id));
                    }
                    return saleRepository.deleteItemsBySaleId(id)
                            .then(saleRepository.deleteById(id));
                });
    }

    @Override
    public Mono<Void> revokeSale(UUID id) {
        log.debug("Anulando venta con ID: {}", id);
        return saleRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Venta no encontrada con ID: " + id)))
                .flatMap(sale -> {
                    sale.setStatus("Revocado");
                    sale.setUpdatedAt(LocalDateTime.now());
                    return saleRepository.update(sale);
                })
                .then();
    }

    @Override
    public Mono<Void> restoreSale(UUID id) {
        log.debug("Restaurando venta con ID: {}", id);
        return saleRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Venta no encontrada con ID: " + id)))
                .flatMap(sale -> {
                    sale.setStatus("Consignado");
                    sale.setUpdatedAt(LocalDateTime.now());
                    return saleRepository.update(sale);
                })
                .then();
    }
}
