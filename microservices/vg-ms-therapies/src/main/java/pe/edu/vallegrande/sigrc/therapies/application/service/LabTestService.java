package pe.edu.vallegrande.sigrc.therapies.application.service;

import pe.edu.vallegrande.sigrc.therapies.domain.model.LabTest;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.LabTestRepository;
import pe.edu.vallegrande.sigrc.therapies.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class LabTestService {

    private final LabTestRepository repository;

    public LabTestService(LabTestRepository repository) {
        this.repository = repository;
    }

    public Flux<LabTest> findAll() {
        return repository.findAll();
    }

    public Flux<LabTest> findAllPaged(int page, int size) {
        return repository.findAllPaged(page, size);
    }

    public Mono<Long> countAll() {
        return repository.countAll();
    }

    public Mono<LabTest> findById(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabTest not found: " + id)));
    }

    public Mono<LabTest> create(LabTest labTest) {
        labTest.setStatus(labTest.getStatus() != null ? labTest.getStatus() : "ACTIVE");
        labTest.setCreatedAt(LocalDateTime.now());
        labTest.setUpdatedAt(LocalDateTime.now());
        return repository.save(labTest);
    }

    public Mono<LabTest> update(String id, LabTest labTest) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabTest not found: " + id)))
            .flatMap(existing -> {
                existing.setName(labTest.getName());
                existing.setPrice(labTest.getPrice());
                if (labTest.getStatus() != null) existing.setStatus(labTest.getStatus());
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabTest not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing).then(Mono.empty());
            });
    }

    public Mono<LabTest> deactivate(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabTest not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("INACTIVE");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<LabTest> restore(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("LabTest not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("ACTIVE");
                existing.setDeletedAt(null);
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }
}
