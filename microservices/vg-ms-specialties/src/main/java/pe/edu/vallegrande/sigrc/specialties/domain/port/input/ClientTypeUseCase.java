package pe.edu.vallegrande.sigrc.specialties.domain.port.input;

import pe.edu.vallegrande.sigrc.specialties.domain.model.ClientType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientTypeUseCase {
    Flux<ClientType> getAllClientTypes();
    Flux<ClientType> getClientTypesPaged(int page, int size);
    Mono<Long> countClientTypes();
    Mono<ClientType> getClientTypeById(String id);
    Flux<ClientType> getActiveClientTypes();
    
    Mono<ClientType> registerClientType(ClientType clientType);
    Mono<ClientType> modifyClientType(String id, ClientType clientType);
    Mono<ClientType> activateClientType(String id);
    Mono<ClientType> deactivateClientType(String id);
}
