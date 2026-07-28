package pe.edu.vallegrade.sigrc.treatments.application.usecase;



import lombok.RequiredArgsConstructor;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.NotFoundException;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IGetTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class GetTratamientoUseCaseImpl implements IGetTratamientoUseCase {

    private final ITratamientoRepository repository;

    @Override
    public Mono<Tratamiento> getById(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(
                new NotFoundException("Tratamiento no encontrado con id: " + id)
            ));
    }

    @Override
    public Flux<Tratamiento> getAll() {
        return repository.findAll();
    }
}
