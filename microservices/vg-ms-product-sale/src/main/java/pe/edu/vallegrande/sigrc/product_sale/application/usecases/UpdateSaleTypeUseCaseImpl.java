package pe.edu.vallegrande.sigrc.product_sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product_sale.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.IUpdateSaleTypeUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductSaleRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateSaleTypeUseCaseImpl implements IUpdateSaleTypeUseCase {

    private final IProductSaleRepository saleRepository;

    @Override
    public Mono<Void> updateSaleType(UUID id, String saleType) {
        log.debug("Actualizando tipo de venta con ID: {} a tipo: {}", id, saleType);
        return saleRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Venta no encontrada con ID: " + id)))
                .flatMap(sale -> {
                    String normalizedType = normalizeSaleType(saleType);
                    sale.setSaleType(normalizedType);
                    sale.setUpdatedAt(LocalDateTime.now());
                    return saleRepository.update(sale);
                })
                .then();
    }

    private String normalizeSaleType(String saleType) {
        String lower = saleType.toLowerCase();
        if (lower.contains("donacion") || lower.contains("donada") || lower.contains("donado") || lower.contains("donación")) {
            return "Donacion";
        }
        return "Vendido";
    }
}
