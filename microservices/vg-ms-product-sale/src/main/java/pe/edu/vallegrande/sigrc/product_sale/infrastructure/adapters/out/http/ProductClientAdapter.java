package pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.ProductInfo;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IProductClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http.HttpClientUtils.str;
import static pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http.HttpClientUtils.toInt;

@Slf4j
@Component
public class ProductClientAdapter implements IProductClient {

    private final WebClient webClient;

    public ProductClientAdapter(@Qualifier("productsWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<ProductInfo> findById(UUID productId) {
        log.debug("Llamando a ms-products: GET /api/v1/products/{}", productId);
        return webClient.get()
                .uri("/api/v1/products/{id}", productId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .doOnNext(body -> log.debug("Respuesta ms-products para {}: {}", productId, body))
                .map(body -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> data = (Map<String, Object>) body.get("data");
                    if (data == null) {
                        log.warn("ms-products devolvio data=null para ID {}", productId);
                        return ProductInfo.builder().id(productId.toString()).build();
                    }
                    BigDecimal salePrice = null;
                    if (data.get("salePrice") != null) {
                        salePrice = new BigDecimal(data.get("salePrice").toString());
                    }
                    Character status = null;
                    if (data.get("status") != null) {
                        status = data.get("status").toString().charAt(0);
                    }
                    return ProductInfo.builder()
                            .id(str(data, "id"))
                            .commercialName(str(data, "commercialName"))
                            .salePrice(salePrice)
                            .stock(toInt(data.get("stock")))
                            .status(status)
                            .build();
                })
                .doOnError(ex -> log.error("ERROR ms-products para ID {}: {}", productId, ex.getMessage()))
                .onErrorResume(ex -> Mono.just(ProductInfo.builder().id(productId.toString()).build()));
    }

    @Override
    public Mono<Void> decrementStock(UUID productId, int quantity) {
        log.debug("Llamando a ms-products: PATCH /api/v1/products/{}/stock?quantity={}", productId, quantity);
        return webClient.patch()
                .uri("/api/v1/products/{id}/stock?quantity={qty}", productId, quantity)
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.BAD_REQUEST), response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Stock insuficiente: " + body))))
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), response ->
                        Mono.error(new RuntimeException("Producto no encontrado: " + productId)))
                .bodyToMono(Void.class)
                .doOnSuccess(v -> log.debug("Stock decrementado correctamente para producto {}", productId))
                .doOnError(ex -> log.error("ERROR al decrementar stock de producto {}: {}", productId, ex.getMessage()));
    }
}
