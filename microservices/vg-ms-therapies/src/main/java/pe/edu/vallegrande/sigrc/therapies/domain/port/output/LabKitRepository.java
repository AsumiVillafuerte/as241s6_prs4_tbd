package pe.edu.vallegrande.sigrc.therapies.domain.port.output;

import pe.edu.vallegrande.sigrc.therapies.domain.model.LabKit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LabKitRepository {
    Flux<LabKit> findAll();
    Flux<LabKit> findAllPaged(int page, int size);
    Mono<Long> countAll();
    Mono<LabKit> findById(String id);
    Mono<LabKit> save(LabKit labKit);
    Mono<Void> deleteById(String id);
}
