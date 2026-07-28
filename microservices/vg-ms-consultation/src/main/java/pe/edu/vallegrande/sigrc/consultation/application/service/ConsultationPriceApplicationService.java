package pe.edu.vallegrande.sigrc.consultation.application.service;

import pe.edu.vallegrande.sigrc.consultation.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.consultation.domain.model.ConsultationPrice;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.ConsultationPriceUseCase;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.ConsultationPriceRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Supplier;

public class ConsultationPriceApplicationService implements ConsultationPriceUseCase {

    private final ConsultationPriceRepositoryPort repositoryPort;

    public ConsultationPriceApplicationService(ConsultationPriceRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Flux<ConsultationPrice> getAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<ConsultationPrice> getPaged(int page, int size) {
        if (page < 0 || size <= 0) {
            return Flux.error((Supplier<Throwable>) () -> new IllegalArgumentException("Invalid pagination parameters"));
        }
        return repositoryPort.findAllPaged(page, size);
    }

    @Override
    public Mono<Long> count() {
        return repositoryPort.countAll();
    }

    @Override
    public Mono<ConsultationPrice> getById(String id) {
        if (id == null || id.isBlank()) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El ID proporcionado no es v\u00e1lido"));
        }
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error((Supplier<Throwable>) () -> new NotFoundException("Precio de consulta no encontrado con ID: " + id)));
    }

    @Override
    public Flux<ConsultationPrice> getBySpecialty(String specialtyId) {
        return repositoryPort.findBySpecialtyId(specialtyId);
    }

    @Override
    public Mono<ConsultationPrice> register(ConsultationPrice consultationPrice) {
        if (consultationPrice == null) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El precio de consulta no puede ser nulo"));
        }
        return Mono.defer(() -> {
            consultationPrice.initializeForCreation();
            return repositoryPort.save(consultationPrice);
        });
    }

    @Override
    public Mono<ConsultationPrice> modify(String id, ConsultationPrice updatedData) {
        return this.getById(id)
                .flatMap(current -> {
                    current.updateInfo(updatedData.getSpecialtyId(), updatedData.getPrice());
                    return repositoryPort.save(current);
                });
    }

    @Override
    public Mono<ConsultationPrice> activate(String id) {
        return this.getById(id)
                .flatMap(consultationPrice -> {
                    consultationPrice.activate();
                    return repositoryPort.save(consultationPrice);
                });
    }

    @Override
    public Mono<ConsultationPrice> deactivate(String id) {
        return this.getById(id)
                .flatMap(consultationPrice -> {
                    consultationPrice.deactivate();
                    return repositoryPort.save(consultationPrice);
                });
    }
}
