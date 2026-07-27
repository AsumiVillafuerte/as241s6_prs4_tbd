package pe.edu.vallegrande.sigrc.specialties.domain.port.input;

import pe.edu.vallegrande.sigrc.specialties.domain.model.CommonStatus;
import pe.edu.vallegrande.sigrc.specialties.domain.model.Specialty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpecialtyUseCase {
    Flux<Specialty> getAllSpecialties();
    Flux<Specialty> getSpecialtiesPaged(int page, int size);
    Mono<Long> countSpecialties();
    Mono<Specialty> getSpecialtyById(String id);
    Mono<Specialty> getSpecialtyByCode(String code);
    Flux<Specialty> getSpecialtiesByStatus(CommonStatus status);
    
    Mono<Specialty> registerSpecialty(Specialty specialty);
    Mono<Specialty> modifySpecialty(String id, Specialty specialty);
    Mono<Specialty> activateSpecialty(String id);
    Mono<Specialty> deactivateSpecialty(String id);
}
