package pe.edu.vallegrande.sigrc.product_sale.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.request.ProductItemRequest;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.request.ProductSaleRequest;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductInfo;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductSaleResponse;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.UserInfo;
import pe.edu.vallegrande.sigrc.product_sale.application.mappers.ProductSaleMapper;
import pe.edu.vallegrande.sigrc.product_sale.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.product_sale.domain.exceptions.PatientNotEligibleException;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSale;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSaleItem;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.ICreateProductSaleUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.in.IGetProductSaleUseCase;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IPatientClient;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductClient;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductSaleRepository;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IUserClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProductSaleUseCaseImpl implements ICreateProductSaleUseCase {

    private static final Set<String> TIPOS_PERMITIDOS = Set.of("Adulto", "Adulto mayor");
    private static final Set<String> ROLES_PERMITIDOS = Set.of("ADMIN", "CAJERO");

    private final IProductSaleRepository saleRepository;
    private final ProductSaleMapper saleMapper;
    private final IPatientClient patientClient;
    private final IProductClient productClient;
    private final IUserClient userClient;
    @Lazy
    private final IGetProductSaleUseCase getSaleUseCase;

    @Override
    public Mono<ProductSaleResponse> createSale(ProductSaleRequest request) {
        log.debug("Creando venta para paciente: {}", request.getPatientId());

        if (request.getPatientId() == null) {
            return Mono.error(new DomainException("El ID del paciente es obligatorio"));
        }
        if (request.getUserId() == null || request.getUserId().isBlank()) {
            return Mono.error(new DomainException("El ID del usuario es obligatorio"));
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return Mono.error(new DomainException("La venta debe tener al menos un ítem"));
        }
        Set<UUID> seenProducts = new HashSet<>();
        for (ProductItemRequest item : request.getItems()) {
            if (!seenProducts.add(item.getProductId())) {
                return Mono.error(new DomainException(
                        "Producto duplicado en la venta: " + item.getProductId()));
            }
        }

        return validateUser(request.getUserId())
                .then(validatePatient(request))
                .then(enrichItemsWithPrices(request.getItems()))
                .flatMap(enrichedItems -> {
                    request.setItems(enrichedItems);
                    return createSaleInternal(request);
                });
    }

    private Mono<Void> validateUser(String userId) {
        log.debug("Validando usuario: {}", userId);
        return userClient.findById(userId)
                .switchIfEmpty(Mono.<UserInfo>error(new DomainException("Usuario no encontrado: " + userId)))
                .flatMap(user -> {
                    String status = user.getStatus();
                    if (status == null || !"ACTIVE".equalsIgnoreCase(status)) {
                        return Mono.<Void>error(new DomainException(
                                "El usuario no se encuentra activo. No puede realizar ventas."));
                    }
                    List<String> roles = user.getRoles();
                    boolean tieneRol = roles != null && roles.stream()
                            .anyMatch(r -> ROLES_PERMITIDOS.stream()
                                    .anyMatch(p -> p.equalsIgnoreCase(r)));
                    if (!tieneRol) {
                        return Mono.<Void>error(new DomainException(
                                "El usuario no tiene permisos para realizar ventas. Roles requeridos: ADMIN o CAJERO"));
                    }
                    log.debug("Usuario validado correctamente: {}, roles: {}", userId, roles);
                    return Mono.empty();
                });
    }

    private Mono<Void> validatePatient(ProductSaleRequest request) {
        log.debug("Validando paciente: {}", request.getPatientId());
        return patientClient.findById(request.getPatientId().toString())
                .switchIfEmpty(Mono.error(new DomainException("Paciente no encontrado: " + request.getPatientId())))
                .flatMap(patient -> {
                    String status = patient.getStatus();
                    if (status != null && !"ACTIVE".equalsIgnoreCase(status)) {
                        return Mono.<Void>error(new DomainException(
                                "El paciente '" + patient.getFirstName() + " " + patient.getLastName()
                                + "' no se encuentra activo y no puede realizar compras."));
                    }
                    String tipo = patient.getPatientType();
                    log.debug("Tipo de paciente {}: {}", request.getPatientId(), tipo);
                    if (tipo != null && TIPOS_PERMITIDOS.stream()
                            .noneMatch(t -> t.equalsIgnoreCase(tipo))) {
                        return Mono.<Void>error(new PatientNotEligibleException(
                                "El paciente '" + patient.getFirstName() + " " + patient.getLastName()
                                + "' es un " + tipo + " y no puede realizar compras. "
                                + "Solo se permiten compras para pacientes adultos o adultos mayores."));
                    }
                    log.debug("Paciente validado correctamente: {}", request.getPatientId());
                    return Mono.empty();
                })
                .onErrorResume(ex -> {
                    if (ex instanceof DomainException) {
                        return Mono.error(ex);
                    }
                    log.warn("No se pudo verificar el paciente {}: {}. Se permite la venta.",
                            request.getPatientId(), ex.getMessage());
                    return Mono.empty();
                });
    }

    private Mono<List<ProductItemRequest>> enrichItemsWithPrices(List<ProductItemRequest> items) {
        log.debug("Validando y obteniendo precios de {} productos", items.size());
        return Flux.fromIterable(items)
                .flatMap(item -> productClient.findById(item.getProductId())
                        .switchIfEmpty(Mono.<ProductInfo>error(new DomainException(
                                "Producto no encontrado: " + item.getProductId())))
                        .flatMap(product -> {
                            Character status = product.getStatus();
                            if (status == null || (status != 'A' && status != 'a')) {
                                String name = product.getCommercialName() != null
                                        ? product.getCommercialName() : item.getProductId().toString();
                                return Mono.<ProductItemRequest>error(new DomainException(
                                        "El producto '" + name + "' no se encuentra activo"));
                            }
                            Integer stock = product.getStock();
                            int qty = item.getQuantity() != null ? item.getQuantity() : 0;
                            if (stock == null || stock < qty) {
                                String name = product.getCommercialName() != null
                                        ? product.getCommercialName() : item.getProductId().toString();
                                return Mono.<ProductItemRequest>error(new DomainException(
                                        "Stock insuficiente para el producto '" + name
                                        + "'. Disponible: " + (stock != null ? stock : 0)
                                        + ", solicitado: " + qty));
                            }
                            BigDecimal unitPrice = item.getUnitPrice() != null
                                    ? item.getUnitPrice()
                                    : product.getSalePrice();
                            if (unitPrice == null) {
                                String name = product.getCommercialName() != null
                                        ? product.getCommercialName() : item.getProductId().toString();
                                return Mono.<ProductItemRequest>error(new DomainException(
                                        "El producto '" + name + "' no tiene precio de venta configurado"));
                            }
                            log.debug("Producto {}: precio unitario = {}", product.getCommercialName(), unitPrice);
                            return Mono.just(ProductItemRequest.builder()
                                    .productId(item.getProductId())
                                    .quantity(item.getQuantity())
                                    .unitPrice(unitPrice)
                                    .build());
                        }))
                .collectList();
    }

    private Mono<ProductSaleResponse> createSaleInternal(ProductSaleRequest request) {
        ProductSale sale = saleMapper.toDomain(request);
        List<ProductSaleItem> items = sale.getItems();

        return generateTicketNumber()
                .flatMap(ticketNumber -> {
                    sale.setTicketNumber(ticketNumber);
                    return saleRepository.save(sale);
                })
                .flatMap(savedSale -> {
                    log.debug("Venta creada con ID: {} y ticket: {}", savedSale.getId(), savedSale.getTicketNumber());
                    return Flux.fromIterable(items)
                            .map(item -> {
                                item.setSaleId(savedSale.getId());
                                return item;
                            })
                            .flatMap(saleRepository::saveItem)
                            .collectList()
                            .then(Mono.just(savedSale));
                })
                .flatMap(savedSale -> decrementStockForItems(items).thenReturn(savedSale))
                .flatMap(savedSale -> getSaleUseCase.getSaleById(savedSale.getId()));
    }

    private Mono<String> generateTicketNumber() {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return saleRepository.countAll()
                .map(count -> String.format("FAR-%s-%04d", dateStr, count + 1));
    }

    private Mono<Void> decrementStockForItems(List<ProductSaleItem> items) {
        return Flux.fromIterable(items)
                .flatMap(item -> productClient.decrementStock(item.getProductId(), item.getQuantity())
                        .doOnSuccess(v -> log.info("Stock decrementado: producto={}, cantidad={}",
                                item.getProductId(), item.getQuantity()))
                        .onErrorResume(ex -> {
                            log.error("No se pudo decrementar stock del producto {}: {}. La venta continua.",
                                    item.getProductId(), ex.getMessage());
                            return Mono.empty();
                        }))
                .then();
    }
}
