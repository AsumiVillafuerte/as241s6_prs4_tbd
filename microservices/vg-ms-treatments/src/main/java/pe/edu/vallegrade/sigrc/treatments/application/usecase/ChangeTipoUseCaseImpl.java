package pe.edu.vallegrade.sigrc.treatments.application.usecase;

import java.util.List;

import lombok.RequiredArgsConstructor;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.DomainException;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.NotFoundException;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IChangeTipoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ChangeTipoUseCaseImpl implements IChangeTipoUseCase {

    private final ITratamientoRepository repository;

    private static final List<String> TIPOS_VALIDOS = List.of("venta", "donado");

    @Override
    public Mono<Tratamiento> execute(String id, String nuevoTipo) {

        if (!TIPOS_VALIDOS.contains(nuevoTipo)) {
            return Mono.error(
                    new DomainException("Tipo inválido: " + nuevoTipo +
                            ". Tipos válidos: " + TIPOS_VALIDOS));
        }

        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Tratamiento no encontrado con id: " + id)))
                .flatMap(existing -> {
                    // No se puede cambiar tipo de un tratamiento revocado
                    if ("revocado".equals(existing.getEstado())) {
                        return Mono.error(
                                new DomainException("No se puede modificar un tratamiento revocado"));
                    }
                    return repository.changeTipo(id, nuevoTipo);
                });
    }
}
