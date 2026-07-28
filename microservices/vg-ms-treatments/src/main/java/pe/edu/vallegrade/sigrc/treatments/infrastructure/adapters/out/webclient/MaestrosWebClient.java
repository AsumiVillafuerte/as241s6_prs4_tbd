package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.NotFoundException;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.ClientApiResponse;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.DoctorClientResponse;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.PatientClientResponse;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.SpecialtyClientResponse;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.TreatmentMaestroClientResponse;
import reactor.core.publisher.Mono;

@Component
public class MaestrosWebClient {

    @Qualifier("gatewayWebClient")
    private final WebClient gatewayWebClient;

    public MaestrosWebClient(@Qualifier("gatewayWebClient") WebClient gatewayWebClient) {
        this.gatewayWebClient = gatewayWebClient;
    }

    // ── Paciente ──────────────────────────────────────────────
    public Mono<PatientClientResponse> getPatient(String patientId) {
        return gatewayWebClient.get()
                .uri("/api/v1/patients/{id}", patientId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ClientApiResponse<PatientClientResponse>>() {
                })
                .doOnNext(r -> System.out.println("PATIENT RAW: " + r))
                .map(ClientApiResponse::getData);
    }

    public Mono<DoctorClientResponse> getDoctor(String doctorId) {
        return gatewayWebClient.get()
                .uri("/api/v1/doctors/{id}", doctorId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ClientApiResponse<DoctorClientResponse>>() {
                })
                .doOnNext(r -> System.out.println("DOCTOR RAW: " + r))
                .map(ClientApiResponse::getData);
    }

    public Mono<SpecialtyClientResponse> getSpecialty(String specialtyId) {
        return gatewayWebClient.get()
                .uri("/api/v1/specialties/{id}", specialtyId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(
                                new NotFoundException("Especialidad no encontrada con id: " + specialtyId)))
                .bodyToMono(SpecialtyClientResponse.class)
                .doOnNext(r -> System.out.println("SPECIALTY RAW: " + r));
    }

    public Mono<TreatmentMaestroClientResponse> getTreatmentMaestro(String treatmentId) {
        return gatewayWebClient.get()
                .uri("/api/v1/therapies/treatments/{id}", treatmentId) // ← URL cambiada
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(
                                new NotFoundException("Tratamiento maestro no encontrado con id: " + treatmentId)))
                .bodyToMono(TreatmentMaestroClientResponse.class);
    }
}
