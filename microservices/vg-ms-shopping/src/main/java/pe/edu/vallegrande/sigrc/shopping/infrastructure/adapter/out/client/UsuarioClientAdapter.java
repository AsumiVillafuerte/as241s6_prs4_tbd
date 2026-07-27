package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.ExternalServiceException;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.shopping.domain.model.UsuarioInfo;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IUsuarioClient;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client.dto.ExternalApiResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UsuarioClientAdapter implements IUsuarioClient {

    private final WebClient gatewayWebClient;

    @Override
    public Mono<UsuarioInfo> findById(String usuarioId) {
        return gatewayWebClient.get()
                .uri("/api/v1/users/{id}", usuarioId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new NotFoundException("Usuario no encontrado con id: " + usuarioId)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new ExternalServiceException("ms-users", "Error interno del servicio")))
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<UsuarioData>>() {})
                .map(response -> new UsuarioInfo(
                        response.data().userId(),
                        response.data().firstName(),
                        response.data().lastName(),
                        response.data().role()
                ));
    }

    private record UsuarioData(String userId, String firstName, String lastName, String role, String status) {}
}
