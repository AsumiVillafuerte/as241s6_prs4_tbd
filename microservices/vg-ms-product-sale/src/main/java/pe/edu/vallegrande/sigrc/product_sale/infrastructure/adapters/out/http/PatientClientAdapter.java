package pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.PatientInfo;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IPatientClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http.HttpClientUtils.str;

@Slf4j
@Component
public class PatientClientAdapter implements IPatientClient {

    private final WebClient webClient;

    public PatientClientAdapter(@Qualifier("patientsWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<PatientInfo> findById(String patientId) {
        log.debug("Llamando a ms-patients: GET /api/v1/patients/{}", patientId);
        return webClient.get()
                .uri("/api/v1/patients/{id}", patientId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .doOnNext(body -> log.debug("Respuesta ms-patients para {}: {}", patientId, body))
                .map(body -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> data = (Map<String, Object>) body.get("data");
                    if (data == null) {
                        log.warn("ms-patients devolvió data=null para ID {}", patientId);
                        return PatientInfo.builder().patientId(patientId).build();
                    }
                    return PatientInfo.builder()
                            .patientId(str(data, "patientId"))
                            .firstName(str(data, "firstName"))
                            .lastName(str(data, "lastName"))
                            .documentNumber(str(data, "documentNumber"))
                            .documentType(str(data, "documentType"))
                            .patientType(str(data, "patientType"))
                            .status(str(data, "status"))
                            .build();
                })
                .doOnError(ex -> log.error("ERROR ms-patients para ID {}: {}", patientId, ex.getMessage()))
                .onErrorResume(ex -> Mono.just(PatientInfo.builder().patientId(patientId).build()));
    }
}
