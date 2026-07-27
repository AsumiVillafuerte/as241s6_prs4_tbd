package pe.edu.vallegrande.sigrc.specialties.domain.port.input;

import pe.edu.vallegrande.sigrc.specialties.domain.model.Treatment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TreatmentUseCase {
    Flux<Treatment> getAllTreatments();
    Flux<Treatment> getTreatmentsPaged(int page, int size);
    Mono<Long> countTreatments();
    Mono<Treatment> getTreatmentById(String id);
    Mono<Treatment> getTreatmentByCode(String code);
    Flux<Treatment> getTreatmentsBySpecialty(String specialtyId);
    
    Mono<Treatment> registerTreatment(Treatment treatment);
    Mono<Treatment> modifyTreatment(String id, Treatment treatment);
    Mono<Treatment> activateTreatment(String id);
    Mono<Treatment> deactivateTreatment(String id);
}
