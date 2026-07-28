package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.NotFoundException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class MedicationClient {

    private final WebClient webClient;

    public MedicationClient(@Value("${clients.medications.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Consulta el microservicio ms-medications para obtener los datos de un medicamento por su ID.
     * Lanza NotFoundException si no existe y DomainException si está inactivo o sin stock suficiente.
     */
    public Mono<MedicationClientResponse> findById(String medicationId) {
        log.debug("Consultando medicamento con id: {}", medicationId);
        return webClient.get()
                .uri("/api/v1/medications/{id}", medicationId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new NotFoundException("Medicamento no encontrado con id: " + medicationId)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new DomainException("Error al consultar el servicio de medicamentos")))
                .bodyToMono(MedicationApiResponse.class)
                .map(MedicationApiResponse::getData)
                .flatMap(med -> {
                    if (!"active".equalsIgnoreCase(med.getStatus())) {
                        return Mono.error(new DomainException(
                                "El medicamento '" + med.getGenericName() + "' no está activo"));
                    }
                    if (med.getStock() != null && med.getStock() <= 0) {
                        return Mono.error(new DomainException(
                                "El medicamento '" + med.getGenericName() + "' no tiene stock disponible"));
                    }
                    return Mono.just(med);
                });
    }

    /**
     * Consulta el medicamento sin validar stock. Usado para operaciones de devolución de stock.
     */
    public Mono<MedicationClientResponse> findByIdNoStockCheck(String medicationId) {
        log.debug("Consultando medicamento (sin validar stock) con id: {}", medicationId);
        return webClient.get()
                .uri("/api/v1/medications/{id}", medicationId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new NotFoundException("Medicamento no encontrado con id: " + medicationId)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new DomainException("Error al consultar el servicio de medicamentos")))
                .bodyToMono(MedicationApiResponse.class)
                .map(MedicationApiResponse::getData);
    }

    /**
     * Descuenta el stock de un medicamento en ms-medications.
     * Llama a PUT /api/v1/medications/{id} con el nuevo stock calculado.
     */
    public Mono<Void> decreaseStock(String medicationId, int currentStock, int quantity) {
        int newStock = currentStock - quantity;
        log.debug("Actualizando stock del medicamento {}: {} -> {}", medicationId, currentStock, newStock);

        return webClient.put()
                .uri("/api/v1/medications/{id}", medicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("stock", newStock))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new NotFoundException("Medicamento no encontrado al actualizar stock: " + medicationId)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new DomainException("Error al actualizar stock en el servicio de medicamentos")))
                .bodyToMono(Void.class);
    }
}
