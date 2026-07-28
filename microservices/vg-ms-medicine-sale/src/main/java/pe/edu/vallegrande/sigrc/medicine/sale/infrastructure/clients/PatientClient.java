package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.DomainException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PatientClient {

    private final WebClient webClient;

    public PatientClient(@Value("${clients.patients.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PatientClientResponse> findById(String patientId) {
        log.debug("Consultando paciente con id: {}", patientId);
        return webClient.get()
                .uri("/api/v1/patients/{patientId}", patientId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new NotFoundException("Paciente no encontrado con id: " + patientId)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new DomainException("Error al consultar el servicio de pacientes")))
                .bodyToMono(PatientApiResponse.class)
                .map(PatientApiResponse::getData)
                .flatMap(patient -> {
                    if (!"ACTIVE".equalsIgnoreCase(patient.getStatus())) {
                        return Mono.error(new DomainException("El paciente no está activo"));
                    }
                    return Mono.just(patient);
                });
    }

    @Data
    @NoArgsConstructor
    public static class PatientApiResponse {
        private int status;
        private String message;
        private PatientClientResponse data;
    }
}
