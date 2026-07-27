package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.ExternalServiceException;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;
import pe.edu.vallegrande.sigrc.shopping.domain.model.MedicamentoInfo;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IMedicamentoClient;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client.dto.MedicamentosApiResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class MedicamentoClientAdapter implements IMedicamentoClient {

    private final WebClient gatewayWebClient;

    @Override
    public Mono<MedicamentoInfo> findById(String medicamentoId) {
        return gatewayWebClient.get()
                .uri("/api/v1/medications/{id}", medicamentoId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        r -> Mono.error(new NotFoundException("Medicamento no encontrado con id: " + medicamentoId)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        r -> Mono.error(new ExternalServiceException("ms-medications", "Error interno del servicio")))
                .bodyToMono(new ParameterizedTypeReference<MedicamentosApiResponse<MedicamentoData>>() {})
                .map(r -> new MedicamentoInfo(
                        r.data().id(),
                        r.data().code(),
                        r.data().commercialName(),
                        r.data().genericName() != null ? r.data().genericName() : r.data().commercialName(),
                        r.data().category(),
                        r.data().form(),
                        r.data().minStock(),
                        r.data().location(),
                        r.data().laboratory(),
                        r.data().costPrice(),
                        r.data().salePrice(),
                        r.data().expirationDate()
                ));
    }

    @Override
    public Mono<String> crearLote(MedicamentoInfo medicamentoInfo, DetalleCompraMedicamento detalle) {
        var payload = new CrearMedicamentoPayload(
                medicamentoInfo.code(),
                detalle.getDenominacionGenerica(),
                detalle.getDenominacionComercial(),
                medicamentoInfo.category(),
                detalle.getPresentacion(),
                detalle.getCantidad(),
                detalle.getMinStock() != null ? detalle.getMinStock() : medicamentoInfo.minStock(),
                detalle.getUbicacion(),
                detalle.getLaboratorio(),
                detalle.getPrecioCompra(),
                detalle.getPrecioVenta(),
                detalle.getVencimiento(),
                detalle.getLote()
        );

        log.info("[crearLote] POST /api/v1/medications - payload: {}", payload);

        return gatewayWebClient.post()
                .uri("/api/v1/medications")
                .bodyValue(payload)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class).defaultIfEmpty("(sin body)").flatMap(body -> {
                            log.error("[crearLote] 4xx - status={} body={}", r.statusCode(), body);
                            return Mono.error(new ExternalServiceException("ms-medications", "4xx: " + body));
                        })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class).defaultIfEmpty("(sin body)").flatMap(body -> {
                            log.error("[crearLote] 5xx - status={} body={}", r.statusCode(), body);
                            return Mono.error(new ExternalServiceException("ms-medications", "5xx: " + body));
                        })
                )
                .bodyToMono(new ParameterizedTypeReference<MedicamentosApiResponse<MedicamentoData>>() {})
                .map(r -> r.data().id());
    }

    @Override
    public Mono<Void> eliminarLote(String loteInventarioId) {
        return gatewayWebClient.delete()
                .uri("/api/v1/medications/{id}", loteInventarioId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
                .onStatus(HttpStatusCode::is5xxServerError,
                        r -> Mono.error(new ExternalServiceException("ms-medications", "Error al desactivar lote")))
                .bodyToMono(Void.class)
                .then();
    }

    private record MedicamentoData(
            String id,
            String code,
            String genericName,
            String commercialName,
            String category,
            String form,
            Integer stock,
            Integer minStock,
            String location,
            String laboratory,
            BigDecimal costPrice,
            BigDecimal salePrice,
            LocalDate expirationDate,
            String batchNumber,
            String status
    ) {}

    private record CrearMedicamentoPayload(
            String code,
            String genericName,
            String commercialName,
            String category,
            String form,
            Integer stock,
            Integer minStock,
            String location,
            String laboratory,
            BigDecimal costPrice,
            BigDecimal salePrice,
            LocalDate expirationDate,
            String batchNumber
    ) {}
}
