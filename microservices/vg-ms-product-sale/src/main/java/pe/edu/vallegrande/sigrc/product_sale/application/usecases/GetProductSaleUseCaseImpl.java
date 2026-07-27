package pe.edu.vallegrande.sigrc.product_sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductItemResponse;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductSaleResponse;
import pe.edu.vallegrande.sigrc.product_sale.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSale;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSaleItem;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.IGetProductSaleUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IPatientClient;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductClient;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductSaleRepository;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IUserClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProductSaleUseCaseImpl implements IGetProductSaleUseCase {

    private final IProductSaleRepository saleRepository;
    private final IPatientClient patientClient;
    private final IUserClient userClient;
    private final IProductClient productClient;

    @Override
    public Mono<ProductSaleResponse> getSaleById(UUID id) {
        log.debug("Buscando venta con ID: {}", id);
        return saleRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Venta no encontrada con ID: " + id)))
                .flatMap(this::loadItemsAndEnrich);
    }

    @Override
    public Flux<ProductSaleResponse> getAllSales() {
        log.debug("Listando todas las ventas");
        return saleRepository.findAll()
                .flatMap(this::loadItemsAndEnrich)
                .doOnComplete(() -> log.debug("Listado de ventas completado"));
    }

    @Override
    public Flux<ProductSaleResponse> getSalesByStatus(String status) {
        log.debug("Listando ventas con estado: {}", status);
        return saleRepository.findByStatus(status)
                .flatMap(this::loadItemsAndEnrich)
                .doOnComplete(() -> log.debug("Listado completado"));
    }

    // ─── helpers ────────────────────────────────────────────────────────────────

    private Mono<ProductSaleResponse> loadItemsAndEnrich(ProductSale sale) {
        return saleRepository.findItemsBySaleId(sale.getId())
                .collectList()
                .flatMap(items -> enrichSale(sale, items));
    }

    private Mono<ProductSaleResponse> enrichSale(ProductSale sale, List<ProductSaleItem> items) {

        // Paciente: nombre completo + número de documento
        Mono<String[]> patientMono = patientClient
                .findById(sale.getPatientId().toString())
                .doOnNext(p -> log.debug("Paciente recibido: {} {}", p.getFirstName(), p.getLastName()))
                .map(p -> new String[]{
                        fullName(p.getFirstName(), p.getLastName()),
                        p.getDocumentNumber()
                })
                .doOnError(e -> log.error("Error al obtener paciente {}: {}", sale.getPatientId(), e.getMessage()))
                .onErrorReturn(new String[]{"", ""});

        // Usuario: username como registeredBy
        Mono<String> userMono = userClient
                .findById(sale.getUserId())
                .doOnNext(u -> log.debug("Usuario recibido: {}", u.getUsername()))
                .map(u -> u.getUsername() != null ? u.getUsername() : "")
                .doOnError(e -> log.error("Error al obtener usuario {}: {}", sale.getUserId(), e.getMessage()))
                .onErrorReturn("");

        // Ítems: cada uno enriquecido con el nombre del producto desde ms-products
        Mono<List<ProductItemResponse>> itemsMono = Flux.fromIterable(items)
                .flatMap(item -> productClient.findById(item.getProductId())
                        .doOnNext(p -> log.debug("Producto recibido: {}", p.getCommercialName()))
                        .map(productInfo -> ProductItemResponse.builder()
                                .productId(item.getProductId())
                                .productName(productInfo.getCommercialName())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .subtotal(item.getSubtotal())
                                .build())
                        .doOnError(e -> log.error("Error al obtener producto {}: {}", item.getProductId(), e.getMessage()))
                        .onErrorReturn(ProductItemResponse.builder()
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .subtotal(item.getSubtotal())
                                .build()))
                .collectList();

        return Mono.zip(patientMono, userMono, itemsMono)
                .map(tuple -> ProductSaleResponse.builder()
                        .id(sale.getId())
                        .ticketNumber(sale.getTicketNumber())
                        .saleDate(sale.getSaleDate())
                        .patientName(tuple.getT1()[0])
                        .dni(tuple.getT1()[1])
                        .registeredBy(tuple.getT2())
                        .total(sale.getTotal())
                        .saleType(sale.getSaleType())
                        .status(sale.getStatus())
                        .createdAt(sale.getCreatedAt())
                        .updatedAt(sale.getUpdatedAt())
                        .items(tuple.getT3())
                        .build());
    }

    private String fullName(String first, String last) {
        if (first == null && last == null) return "";
        if (first == null) return last;
        if (last == null) return first;
        return first + " " + last;
    }
}
