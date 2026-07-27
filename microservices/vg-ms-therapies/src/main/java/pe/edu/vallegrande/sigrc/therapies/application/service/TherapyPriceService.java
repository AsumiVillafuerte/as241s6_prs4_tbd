package pe.edu.vallegrande.sigrc.therapies.application.service;

import pe.edu.vallegrande.sigrc.therapies.domain.model.TherapyPrice;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TherapyPriceRepository;
import pe.edu.vallegrande.sigrc.therapies.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class TherapyPriceService {

    private final TherapyPriceRepository repository;

    public TherapyPriceService(TherapyPriceRepository repository) {
        this.repository = repository;
    }

    public Flux<TherapyPrice> findAll() {
        return repository.findAll();
    }

    public Flux<TherapyPrice> findAllPaged(int page, int size) {
        return repository.findAllPaged(page, size);
    }

    public Mono<Long> countAll() {
        return repository.countAll();
    }

    public Flux<TherapyPrice> findBySpecialtyId(String specialtyId) {
        return repository.findBySpecialtyId(specialtyId);
    }

    public Mono<TherapyPrice> findById(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("TherapyPrice not found: " + id)));
    }

    public Mono<TherapyPrice> create(TherapyPrice therapyPrice) {
        therapyPrice.setStatus(therapyPrice.getStatus() != null ? therapyPrice.getStatus() : "ACTIVE");
        therapyPrice.setCreatedAt(LocalDateTime.now());
        therapyPrice.setUpdatedAt(LocalDateTime.now());
        return repository.save(therapyPrice);
    }

    public Mono<TherapyPrice> update(String id, TherapyPrice therapyPrice) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("TherapyPrice not found: " + id)))
            .flatMap(existing -> {
                existing.setSpecialtyId(therapyPrice.getSpecialtyId());
                existing.setClientType(therapyPrice.getClientType());
                existing.setPrice(therapyPrice.getPrice());
                if (therapyPrice.getStatus() != null) existing.setStatus(therapyPrice.getStatus());
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("TherapyPrice not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing).then(Mono.empty());
            });
    }

    public Mono<TherapyPrice> deactivate(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("TherapyPrice not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<TherapyPrice> restore(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("TherapyPrice not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("ACTIVE");
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }
}
