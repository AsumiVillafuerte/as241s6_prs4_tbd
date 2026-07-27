package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.ExternalServiceException;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.shopping.domain.model.ProveedorInfo;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IProveedorClient;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client.dto.ExternalApiResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProveedorClientAdapter implements IProveedorClient {

    private final WebClient gatewayWebClient;

    @Override
    public Mono<ProveedorInfo> findById(Long proveedorId) {
        return gatewayWebClient.get()
                .uri("/api/v1/suppliers/{id}", proveedorId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new NotFoundException("Proveedor no encontrado con id: " + proveedorId)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new ExternalServiceException("ms-suppliers", "Error interno del servicio")))
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<ProveedorData>>() {})
                .map(response -> new ProveedorInfo(
                        response.data().supplierId(),
                        response.data().businessName(),
                        response.data().documentNumber(),
                        response.data().status()
                ));
    }

    private record ProveedorData(
            Long supplierId,
            String businessName,
            String documentType,
            String documentNumber,
            String address,
            String phone,
            String email,
            Boolean status
    ) {}
}
