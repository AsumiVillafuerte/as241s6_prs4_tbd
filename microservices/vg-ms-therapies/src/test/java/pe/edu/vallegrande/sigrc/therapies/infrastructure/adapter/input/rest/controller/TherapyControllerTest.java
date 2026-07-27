package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.sigrc.therapies.application.service.TherapyService;
import pe.edu.vallegrande.sigrc.therapies.domain.model.Therapy;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper.WebMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.TherapyRequest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.TherapyResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(TherapyController.class)
class TherapyControllerTest {

    @Autowired private WebTestClient webTestClient;
    @MockitoBean private TherapyService service;
    @MockitoBean private WebMapper mapper;

    private final Therapy domain = Therapy.builder()
        .id("1").ticket("TRAT-2025-06-10-000001").specialtyId("s1")
        .clientType("PUBLICO").medic("m1").medicName("Dr. Uno")
        .patient("p1").patientName("Paciente Uno").dni("12345678")
        .registerBy("r1").registerByName("Registrador Uno")
        .total(BigDecimal.valueOf(100)).type("venta")
        .tarjeta("efectivo").status("CONSIGNADO")
        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
        .build();

    private final TherapyResponse response = TherapyResponse.builder()
        .id("1").ticket("TRAT-2025-06-10-000001").specialtyId("s1")
        .clientType("PUBLICO").medic("m1").medicName("Dr. Uno")
        .patient("p1").patientName("Paciente Uno").dni("12345678")
        .registerBy("r1").registerByName("Registrador Uno")
        .total(BigDecimal.valueOf(100)).type("venta")
        .tarjeta("efectivo").status("CONSIGNADO")
        .createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt())
        .build();

    @Test
    void getAll_returnsList() {
        when(service.findAll()).thenReturn(Flux.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);

        webTestClient.get().uri("/api/v1/therapies")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(TherapyResponse.class).hasSize(1);
    }

    @Test
    void getById_returnsItem() {
        when(service.findById("1")).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);

        webTestClient.get().uri("/api/v1/therapies/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(TherapyResponse.class);
    }

    @Test
    void getById_notFound_returns404() {
        when(service.findById("999"))
            .thenReturn(Mono.error(new pe.edu.vallegrande.sigrc.therapies.domain.exception.NotFoundException("not found")));

        webTestClient.get().uri("/api/v1/therapies/999")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void create_returnsCreated() {
        TherapyRequest request = new TherapyRequest();
        request.setSpecialtyId("s1"); request.setClientType("PUBLICO");
        request.setMedic("m1"); request.setMedicName("Dr. Uno");
        request.setPatient("p1"); request.setPatientName("Paciente Uno");
        request.setDni("12345678"); request.setRegisterBy("r1"); request.setRegisterByName("Registrador Uno");
        request.setTotal(BigDecimal.valueOf(100)); request.setType("venta");

        when(mapper.toDomain(any(TherapyRequest.class))).thenReturn(domain);
        when(service.create(any(Therapy.class))).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);

        webTestClient.post().uri("/api/v1/therapies")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(TherapyResponse.class);
    }

    @Test
    void update_returnsUpdated() {
        TherapyRequest request = new TherapyRequest();
        request.setSpecialtyId("s1"); request.setClientType("PUBLICO");
        request.setMedic("m1"); request.setMedicName("Dr. Uno");
        request.setPatient("p1"); request.setPatientName("Paciente Uno");
        request.setDni("12345678"); request.setRegisterBy("r1"); request.setRegisterByName("Registrador Uno");
        request.setTotal(BigDecimal.valueOf(200)); request.setType("venta");

        Therapy updated = Therapy.builder().id("1").total(BigDecimal.valueOf(200)).build();
        TherapyResponse updatedResp = TherapyResponse.builder().id("1").total(BigDecimal.valueOf(200)).build();

        when(mapper.toDomain(any(TherapyRequest.class))).thenReturn(updated);
        when(service.update(eq("1"), any(Therapy.class))).thenReturn(Mono.just(updated));
        when(mapper.toResponse(updated)).thenReturn(updatedResp);

        webTestClient.put().uri("/api/v1/therapies/1")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody(TherapyResponse.class);
    }

    @Test
    void delete_returnsNoContent() {
        when(service.delete("1")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/therapies/1")
            .exchange()
            .expectStatus().isNoContent();
    }
}
