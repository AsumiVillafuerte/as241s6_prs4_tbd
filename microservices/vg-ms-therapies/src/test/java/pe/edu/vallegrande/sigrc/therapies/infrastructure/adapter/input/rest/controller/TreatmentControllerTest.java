package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.sigrc.therapies.application.service.TreatmentService;
import pe.edu.vallegrande.sigrc.therapies.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper.WebMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.TreatmentRequest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.TreatmentResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(TreatmentController.class)
class TreatmentControllerTest {

    @Autowired private WebTestClient webTestClient;
    @MockitoBean private TreatmentService service;
    @MockitoBean private WebMapper mapper;

    private final Treatment domain = Treatment.builder()
        .id("1").code("T001").name("Treatment 1").specialtyId("s1")
        .salePrice(BigDecimal.valueOf(80)).status("ACTIVE")
        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

    private final TreatmentResponse response = TreatmentResponse.builder()
        .id("1").code("T001").name("Treatment 1").specialtyId("s1")
        .salePrice(BigDecimal.valueOf(80)).status("ACTIVE")
        .createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();

    @Test void getAll() {
        when(service.findAll()).thenReturn(Flux.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/treatments").exchange().expectStatus().isOk();
    }

    @Test void getById() {
        when(service.findById("1")).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/treatments/1").exchange().expectStatus().isOk();
    }

    @Test void getBySpecialty() {
        when(service.findBySpecialtyId("s1")).thenReturn(Flux.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/treatments/by-specialty/s1").exchange().expectStatus().isOk();
    }

    @Test void create() {
        TreatmentRequest req = new TreatmentRequest();
        req.setCode("T001"); req.setName("Treatment 1"); req.setSpecialtyId("s1");
        when(mapper.toDomain(any(TreatmentRequest.class))).thenReturn(domain);
        when(service.create(any(Treatment.class))).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.post().uri("/api/v1/treatments").bodyValue(req).exchange().expectStatus().isCreated();
    }

    @Test void delete() {
        when(service.delete("1")).thenReturn(Mono.empty());
        webTestClient.delete().uri("/api/v1/treatments/1").exchange().expectStatus().isNoContent();
    }
}
