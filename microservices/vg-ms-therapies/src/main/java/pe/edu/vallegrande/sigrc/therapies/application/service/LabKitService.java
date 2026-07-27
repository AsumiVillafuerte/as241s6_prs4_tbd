package pe.edu.vallegrande.sigrc.therapies.application.service;

import pe.edu.vallegrande.sigrc.therapies.domain.model.LabKit;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.LabKitRepository;
import pe.edu.vallegrande.sigrc.therapies.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class LabKitService {

    private final LabKitRepository repository;

    public LabKitService(LabKitRepository repository) {
        this.repository = repository;
    }

    public Flux<LabKit> findAll() {
        return repository.findAll();
    }

    public Flux<LabKit> findAllPaged(int page, int size) {
        return repository.findAllPaged(page, size);
    }

    public Mono<Long> countAll() {
        return repository.countAll();
    }

    public Mono<LabKit> findById(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabKit not found: " + id)));
    }

    public Mono<LabKit> create(LabKit labKit) {
        labKit.setStatus(labKit.getStatus() != null ? labKit.getStatus() : "ACTIVE");
        labKit.setCreatedAt(LocalDateTime.now());
        labKit.setUpdatedAt(LocalDateTime.now());
        return repository.save(labKit);
    }

    public Mono<LabKit> update(String id, LabKit labKit) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabKit not found: " + id)))
            .flatMap(existing -> {
                existing.setName(labKit.getName());
                existing.setItems(labKit.getItems());
                if (labKit.getStatus() != null) existing.setStatus(labKit.getStatus());
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabKit not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing).then(Mono.empty());
            });
    }

    public Mono<LabKit> deactivate(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabKit not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<LabKit> restore(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabKit not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("ACTIVE");
                existing.setDeletedAt(null);
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }
}
