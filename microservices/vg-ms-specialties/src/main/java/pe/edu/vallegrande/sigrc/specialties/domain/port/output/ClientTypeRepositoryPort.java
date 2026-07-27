package pe.edu.vallegrande.sigrc.specialties.domain.port.output;

import pe.edu.vallegrande.sigrc.specialties.domain.model.ClientType;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientTypeRepositoryPort {
    Flux<ClientType> findAll();
    Flux<ClientType> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<ClientType> findById(String id);
    Flux<ClientType> findByStatus(CommonStatus status);
    Mono<ClientType> save(ClientType clientType);
}
