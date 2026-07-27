package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.sigrc.therapies.application.service.LabKitService;
import pe.edu.vallegrande.sigrc.therapies.domain.model.LabKit;
import pe.edu.vallegrande.sigrc.therapies.domain.model.LabKitItem;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper.WebMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.LabKitRequest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.LabKitResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(LabKitController.class)
class LabKitControllerTest {

    @Autowired private WebTestClient webTestClient;
    @MockitoBean private LabKitService service;
    @MockitoBean private WebMapper mapper;

    private final LabKitItem item = LabKitItem.builder()
        .labTestId("lt1").prueba("Vitamina B12").labTestName("Vitamina B12").labTestPrice(BigDecimal.valueOf(30))
        .build();

    private final LabKit domain = LabKit.builder()
        .id("1").name("LabKit 1").items(List.of(item))
        .status("ACTIVE").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

    private final LabKitResponse response = LabKitResponse.builder()
        .id("1").name("LabKit 1").items(null)
        .status("ACTIVE").createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();

    @Test void getAll() {
        when(service.findAll()).thenReturn(Flux.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/laboratory/kits").exchange().expectStatus().isOk();
    }

    @Test void getById() {
        when(service.findById("1")).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/laboratory/kits/1").exchange().expectStatus().isOk();
    }

    @Test void create() {
        LabKitRequest req = new LabKitRequest();
        req.setName("LabKit 1");
        when(mapper.toDomain(any(LabKitRequest.class))).thenReturn(domain);
        when(service.create(any(LabKit.class))).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.post().uri("/api/v1/laboratory/kits").bodyValue(req).exchange().expectStatus().isCreated();
    }

    @Test void delete() {
        when(service.delete("1")).thenReturn(Mono.empty());
        webTestClient.delete().uri("/api/v1/laboratory/kits/1").exchange().expectStatus().isNoContent();
    }
}
