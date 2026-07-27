package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.sigrc.therapies.application.service.LabTestService;
import pe.edu.vallegrande.sigrc.therapies.domain.model.LabTest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper.WebMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.LabTestRequest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.LabTestResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(LabTestController.class)
class LabTestControllerTest {

    @Autowired private WebTestClient webTestClient;
    @MockitoBean private LabTestService service;
    @MockitoBean private WebMapper mapper;

    private final LabTest domain = LabTest.builder()
        .id("1").name("LabTest 1").price(BigDecimal.valueOf(30))
        .status("ACTIVE").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

    private final LabTestResponse response = LabTestResponse.builder()
        .id("1").name("LabTest 1").price(BigDecimal.valueOf(30))
        .status("ACTIVE").createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();

    @Test void getAll() {
        when(service.findAll()).thenReturn(Flux.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/laboratory/tests").exchange().expectStatus().isOk();
    }

    @Test void getById() {
        when(service.findById("1")).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/laboratory/tests/1").exchange().expectStatus().isOk();
    }

    @Test void create() {
        LabTestRequest req = new LabTestRequest();
        req.setName("LabTest 1"); req.setPrice(BigDecimal.valueOf(30));
        when(mapper.toDomain(any(LabTestRequest.class))).thenReturn(domain);
        when(service.create(any(LabTest.class))).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.post().uri("/api/v1/laboratory/tests").bodyValue(req).exchange().expectStatus().isCreated();
    }

    @Test void delete() {
        when(service.delete("1")).thenReturn(Mono.empty());
        webTestClient.delete().uri("/api/v1/laboratory/tests/1").exchange().expectStatus().isNoContent();
    }
}
