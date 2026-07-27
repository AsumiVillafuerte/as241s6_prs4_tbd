package pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.UserInfo;
import pe.edu.vallegrande.sigrc.product_sale.domain.ports.out.IUserClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http.HttpClientUtils.str;

@Slf4j
@Component
public class UserClientAdapter implements IUserClient {

    private final WebClient webClient;

    public UserClientAdapter(@Qualifier("usersWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<UserInfo> findById(String userId) {
        log.debug("Llamando a ms-users: GET /api/v1/users/{}", userId);
        return webClient.get()
                .uri("/api/v1/users/{id}", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .doOnNext(body -> log.debug("Respuesta ms-users para {}: {}", userId, body))
                .map(body -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> data = (Map<String, Object>) body.get("data");
                    if (data == null) {
                        log.warn("ms-users devolvió data=null para ID {}", userId);
                        return UserInfo.builder().userId(userId).build();
                    }

                    @SuppressWarnings("unchecked")
                    List<String> roles = data.get("roles") instanceof List<?> rawList
                            ? rawList.stream()
                                    .filter(String.class::isInstance)
                                    .map(String.class::cast)
                                    .toList()
                            : List.of();

                    return UserInfo.builder()
                            .userId(str(data, "userId"))
                            .username(str(data, "username"))
                            .roles(roles)
                            .status(str(data, "status"))
                            .build();
                })
                .doOnError(ex -> log.error("ERROR ms-users para ID {}: {}", userId, ex.getMessage()))
                .onErrorResume(ex -> Mono.just(UserInfo.builder().userId(userId).build()));
    }
}
