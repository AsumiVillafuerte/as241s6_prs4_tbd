package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.DomainException;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.exceptions.NotFoundException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${clients.users.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<UserClientResponse> findCashierById(String userId) {
        log.debug("Consultando usuario cajero con id: {}", userId);
        return webClient.get()
                .uri("/api/v1/users/{id}", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new NotFoundException("Usuario no encontrado con id: " + userId)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new DomainException("Error al consultar el servicio de usuarios")))
                .bodyToMono(UserApiResponse.class)
                .map(UserApiResponse::getData)
                .flatMap(user -> {
                    if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
                        return Mono.error(new DomainException("El usuario no está activo"));
                    }
                    if (user.getRoles() == null || !user.getRoles().contains("CAJERO")) {
                        return Mono.error(new DomainException(
                                "El usuario '" + user.getUsername() + "' no tiene el rol de CAJERO"));
                    }
                    return Mono.just(user);
                });
    }

    @Data
    @NoArgsConstructor
    public static class UserApiResponse {
        private int status;
        private String message;
        private UserClientResponse data;
    }
}
