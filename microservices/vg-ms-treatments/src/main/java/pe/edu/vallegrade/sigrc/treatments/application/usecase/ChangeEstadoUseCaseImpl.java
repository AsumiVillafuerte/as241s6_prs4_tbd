package pe.edu.vallegrade.sigrc.treatments.application.usecase;

import java.util.List;



import lombok.RequiredArgsConstructor;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.DomainException;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.NotFoundException;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IChangeEstadoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class ChangeEstadoUseCaseImpl implements IChangeEstadoUseCase {

    private final ITratamientoRepository repository;

    private static final List<String> ESTADOS_VALIDOS =
        List.of("consignado", "revocado", "donado");

    @Override
    public Mono<Tratamiento> execute(String id, String nuevoEstado) {

        // Validar que el estado sea válido
        if (!ESTADOS_VALIDOS.contains(nuevoEstado)) {
            return Mono.error(
                new DomainException("Estado inválido: " + nuevoEstado +
                    ". Estados válidos: " + ESTADOS_VALIDOS)
            );
        }

        return repository.findById(id)
            .switchIfEmpty(Mono.error(
                new NotFoundException("Tratamiento no encontrado con id: " + id)
            ))
            .flatMap(existing -> {
                // No se puede cambiar estado de un tratamiento ya revocado
                if ("revocado".equals(existing.getEstado())) {
                    return Mono.error(
                        new DomainException("No se puede modificar un tratamiento revocado")
                    );
                }

                return repository.changeEstado(id, nuevoEstado);
            });
    }
}
