package pe.edu.vallegrande.sigrc.therapies.domain.port.output;

import pe.edu.vallegrande.sigrc.therapies.domain.model.LabTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LabTestRepository {
    Flux<LabTest> findAll();
    Flux<LabTest> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<LabTest> findById(String id);
    Mono<LabTest> save(LabTest labTest);
    Mono<Void> deleteById(String id);
}
