package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.edu.vallegrande.sigrc.therapies.application.service.TherapyPriceService;
import pe.edu.vallegrande.sigrc.therapies.domain.model.TherapyPrice;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.mapper.WebMapper;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request.TherapyPriceRequest;
import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response.TherapyPriceResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(TherapyPriceController.class)
class TherapyPriceControllerTest {

    @Autowired private WebTestClient webTestClient;
    @MockitoBean private TherapyPriceService service;
    @MockitoBean private WebMapper mapper;

    private final TherapyPrice domain = TherapyPrice.builder()
        .id("1").specialtyId("s1").clientType("PUBLICO")
        .price(BigDecimal.valueOf(50)).status("ACTIVE")
        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

    private final TherapyPriceResponse response = TherapyPriceResponse.builder()
        .id("1").specialtyId("s1").clientType("PUBLICO")
        .price(BigDecimal.valueOf(50)).status("ACTIVE")
        .createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();

    @Test
    void getAll_returnsList() {
        when(service.findAll()).thenReturn(Flux.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/therapy-prices")
            .exchange().expectStatus().isOk()
            .expectBodyList(TherapyPriceResponse.class).hasSize(1);
    }

    @Test
    void getBySpecialty_returnsList() {
        when(service.findBySpecialtyId("s1")).thenReturn(Flux.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/therapy-prices/by-specialty/s1")
            .exchange().expectStatus().isOk()
            .expectBodyList(TherapyPriceResponse.class).hasSize(1);
    }

    @Test
    void getById_returnsItem() {
        when(service.findById("1")).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.get().uri("/api/v1/therapy-prices/1")
            .exchange().expectStatus().isOk()
            .expectBody(TherapyPriceResponse.class);
    }

    @Test
    void create_returnsCreated() {
        TherapyPriceRequest req = new TherapyPriceRequest();
        req.setSpecialtyId("s1"); req.setClientType("PUBLICO"); req.setPrice(BigDecimal.valueOf(50));
        when(mapper.toDomain(any(TherapyPriceRequest.class))).thenReturn(domain);
        when(service.create(any(TherapyPrice.class))).thenReturn(Mono.just(domain));
        when(mapper.toResponse(domain)).thenReturn(response);
        webTestClient.post().uri("/api/v1/therapy-prices")
            .bodyValue(req).exchange().expectStatus().isCreated();
    }

    @Test
    void delete_returnsNoContent() {
        when(service.delete("1")).thenReturn(Mono.empty());
        webTestClient.delete().uri("/api/v1/therapy-prices/1")
            .exchange().expectStatus().isNoContent();
    }
}
