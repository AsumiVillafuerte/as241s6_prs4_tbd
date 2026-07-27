package pe.edu.vallegrande.sigrc.therapies.application.service;

import pe.edu.vallegrande.sigrc.therapies.domain.model.Treatment;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TreatmentRepository;
import pe.edu.vallegrande.sigrc.therapies.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class TreatmentService {

    private final TreatmentRepository repository;

    public TreatmentService(TreatmentRepository repository) {
        this.repository = repository;
    }

    public Flux<Treatment> findAll() {
        return repository.findAll();
    }

    public Flux<Treatment> findAllPaged(int page, int size) {
        return repository.findAllPaged(page, size);
    }

    public Mono<Long> countAll() {
        return repository.countAll();
    }

    public Flux<Treatment> findBySpecialtyId(String specialtyId) {
        return repository.findBySpecialtyId(specialtyId);
    }

    public Mono<Treatment> findById(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Treatment not found: " + id)));
    }

    public Mono<Treatment> create(Treatment treatment) {
        treatment.setStatus(treatment.getStatus() != null ? treatment.getStatus() : "ACTIVE");
        treatment.setCreatedAt(LocalDateTime.now());
        treatment.setUpdatedAt(LocalDateTime.now());
        return repository.save(treatment);
    }

    public Mono<Treatment> update(String id, Treatment treatment) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Treatment not found: " + id)))
            .flatMap(existing -> {
                if (treatment.getCode() != null) existing.setCode(treatment.getCode());
                existing.setName(treatment.getName());
                existing.setSpecialtyId(treatment.getSpecialtyId());
                existing.setSalePrice(treatment.getSalePrice());
                if (treatment.getStatus() != null) existing.setStatus(treatment.getStatus());
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Treatment not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing).then(Mono.empty());
            });
    }

    public Mono<Treatment> deactivate(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Treatment not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<Treatment> restore(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Treatment not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("ACTIVE");
                existing.setDeletedAt(null);
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }
}
