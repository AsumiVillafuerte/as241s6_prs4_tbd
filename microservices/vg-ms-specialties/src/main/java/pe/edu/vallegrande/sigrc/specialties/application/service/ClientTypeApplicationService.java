package pe.edu.vallegrande.sigrc.specialties.application.service;

import pe.edu.vallegrande.sigrc.specialties.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.specialties.domain.model.ClientType;
import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.port.input.ClientTypeUseCase;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.ClientTypeRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Supplier;

public class ClientTypeApplicationService implements ClientTypeUseCase {

    private final ClientTypeRepositoryPort repositoryPort;

    public ClientTypeApplicationService(ClientTypeRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Flux<ClientType> getAllClientTypes() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<ClientType> getClientTypesPaged(int page, int size) {
        if (page < 0 || size <= 0) {
            return Flux.error((Supplier<Throwable>) () -> new IllegalArgumentException("Parámetros de paginación inválidos"));
        }
        return repositoryPort.findAllPaged(page, size);
    }

    @Override
    public Mono<Long> countClientTypes() {
        return repositoryPort.countAll();
    }

    @Override
    public Mono<ClientType> getClientTypeById(String id) {
        if (id == null || id.isBlank()) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El ID proporcionado no es válido"));
        }
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error((Supplier<Throwable>) () -> new NotFoundException("Tipo de cliente no encontrado con ID: " + id)));
    }

    @Override
    public Flux<ClientType> getActiveClientTypes() {
        return repositoryPort.findByStatus(CommonStatus.ACTIVE);
    }

    @Override
    public Mono<ClientType> registerClientType(ClientType clientType) {
        if (clientType == null) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El tipo de cliente no puede ser nulo"));
        }
        return Mono.defer(() -> {
            clientType.initializeForCreation();
            return repositoryPort.save(clientType);
        });
    }

    @Override
    public Mono<ClientType> modifyClientType(String id, ClientType updatedData) {
        return this.getClientTypeById(id)
                .flatMap(current -> {
                    current.updateInfo(updatedData.getName(), updatedData.getDescription());
                    return repositoryPort.save(current);
                });
    }

    @Override
    public Mono<ClientType> activateClientType(String id) {
        return this.getClientTypeById(id)
                .flatMap(clientType -> {
                    clientType.enable();
                    return repositoryPort.save(clientType);
                });
    }

    @Override
    public Mono<ClientType> deactivateClientType(String id) {
        return this.getClientTypeById(id)
                .flatMap(clientType -> {
                    clientType.disable();
                    return repositoryPort.save(clientType);
                });
    }
}
