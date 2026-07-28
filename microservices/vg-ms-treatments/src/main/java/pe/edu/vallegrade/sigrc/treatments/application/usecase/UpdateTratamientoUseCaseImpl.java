package pe.edu.vallegrade.sigrc.treatments.application.usecase;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.DomainException;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.NotFoundException;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.models.TratamientoItem;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IUpdateTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.MaestrosWebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateTratamientoUseCaseImpl implements IUpdateTratamientoUseCase {

    private final ITratamientoRepository repository;
    private final MaestrosWebClient maestrosWebClient;

    @Override
    public Mono<Tratamiento> execute(String id, Tratamiento tratamiento) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Tratamiento no encontrado con id: " + id)))
                .flatMap(existing -> {
                    if (!"consignado".equals(existing.getEstado())) {
                        return Mono.error(
                                new DomainException("Solo se pueden editar tratamientos consignados"));
                    }

                    // Valida items duplicados
                    List<String> ids = tratamiento.getItems().stream()
                            .map(TratamientoItem::getTratamientoMaestroId)
                            .collect(Collectors.toList());

                    java.util.Set<String> idsUnicos = new java.util.HashSet<>(ids);
                    if (idsUnicos.size() != ids.size()) {
                        return Mono.error(new DomainException(
                                "No se pueden agregar tratamientos duplicados en el mismo registro"
                        ));
                    }

                    // Resuelve precios de los items desde el maestro
                    List<Mono<TratamientoItem>> itemsResueltos = tratamiento.getItems().stream()
                            .map(item -> maestrosWebClient.getTreatmentMaestro(item.getTratamientoMaestroId())
                                    .flatMap(maestro -> {
                                        if (maestro.getSpecialtyId() == null || !maestro.getSpecialtyId().equals(existing.getEspecialidadId())) {
                                            return Mono.error(new DomainException("El ítem " + maestro.getName() + " no pertenece a la especialidad del registro"));
                                        }
                                        double precioReal = maestro.getSalePrice().doubleValue();
                                        double subtotal = item.getCantidad() * precioReal;
                                        return Mono.just(TratamientoItem.builder()
                                                .tratamientoMaestroId(item.getTratamientoMaestroId())
                                                .nombre(maestro.getName())
                                                .cantidad(item.getCantidad())
                                                .precioUnitario(precioReal)
                                                .subtotal(subtotal)
                                                .build());
                                    }))
                            .collect(Collectors.toList());

                    Mono<List<TratamientoItem>> itemsMono = Mono.zip(itemsResueltos, results -> Arrays.stream(results)
                            .map(o -> (TratamientoItem) o)
                            .collect(Collectors.toList()));

                    Mono<Boolean> validateMedicoMono = (tratamiento.getMedicoId() != null && !tratamiento.getMedicoId().isBlank())
                            ? maestrosWebClient.getDoctor(tratamiento.getMedicoId())
                                .flatMap(medico -> {
                                    if (medico.getSpecialtyId() == null || !medico.getSpecialtyId().equals(existing.getEspecialidadId())) {
                                        return Mono.<Boolean>error(new DomainException("El médico seleccionado no tiene la especialidad del registro"));
                                    }
                                    tratamiento.setMedicoNombre(medico.getFirstName() + " " + medico.getLastName());
                                    return Mono.just(true);
                                })
                            : Mono.just(true);

                    return validateMedicoMono.flatMap(valid -> itemsMono.flatMap(itemsConPrecio -> {
                        double total = itemsConPrecio.stream()
                                .mapToDouble(TratamientoItem::getSubtotal)
                                .sum();

                        tratamiento.setItems(itemsConPrecio);
                        tratamiento.setTotal(total);
                        tratamiento.setActualizadoEn(LocalDateTime.now());

                        return repository.update(id, tratamiento);
                    }));
                });
    }
}
