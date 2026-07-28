package pe.edu.vallegrade.sigrc.treatments.application.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pe.edu.vallegrade.sigrc.treatments.domain.exceptions.DomainException;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.models.TratamientoItem;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.ICreateTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.MaestrosWebClient;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.DoctorClientResponse;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.PatientClientResponse;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.dto.SpecialtyClientResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateTratamientoUseCaseImpl implements ICreateTratamientoUseCase {

    private final ITratamientoRepository repository;
    private final MaestrosWebClient maestrosWebClient;

    @Override
    public Mono<Tratamiento> execute(Tratamiento tratamiento) {

        // Valida paciente, médico y especialidad en paralelo
        Mono<PatientClientResponse> pacienteMono = maestrosWebClient.getPatient(tratamiento.getPacienteId());

        Mono<DoctorClientResponse> medicoMono = maestrosWebClient.getDoctor(tratamiento.getMedicoId());

        Mono<SpecialtyClientResponse> especialidadMono = maestrosWebClient
                .getSpecialty(tratamiento.getEspecialidadId());


        // Valida items duplicados
        List<String> ids = tratamiento.getItems().stream()
                .map(TratamientoItem::getTratamientoMaestroId)
                .collect(Collectors.toList());

        Set<String> idsUnicos = new HashSet<>(ids);
        if (idsUnicos.size() != ids.size()) {
            return Mono.error(new DomainException(
                    "No se pueden agregar tratamientos duplicados en el mismo registro"
            ));
        }

        // Por cada item, consulta el precio real del maestro de tratamientos
        List<Mono<TratamientoItem>> itemsResueltos = tratamiento.getItems().stream()
                .map(item -> maestrosWebClient.getTreatmentMaestro(item.getTratamientoMaestroId())
                        .flatMap(maestro -> {
                            if (maestro.getSpecialtyId() == null || !maestro.getSpecialtyId().equals(tratamiento.getEspecialidadId())) {
                                return Mono.error(new DomainException("El ítem " + maestro.getName() + " no pertenece a la especialidad seleccionada"));
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

        Mono<List<TratamientoItem>> itemsMono = Mono.zip(itemsResueltos, results ->
                Arrays.stream(results)
                        .map(o -> (TratamientoItem) o)
                        .collect(Collectors.toList())
        );

        return Mono.zip(pacienteMono, medicoMono, especialidadMono, itemsMono)
                .flatMap(tuple -> {
                    PatientClientResponse paciente = tuple.getT1();
                    DoctorClientResponse medico = tuple.getT2();
                    SpecialtyClientResponse especialidad = tuple.getT3();
                    List<TratamientoItem> itemsConPrecio = tuple.getT4();

                    if (medico.getSpecialtyId() == null || !medico.getSpecialtyId().equals(tratamiento.getEspecialidadId())) {
                        return Mono.error(new DomainException("El médico seleccionado no tiene la especialidad indicada"));
                    }

                    // Sobreescribe con datos reales del maestro
                    tratamiento.setPacienteNombre(
                            paciente.getFirstName() + " " + paciente.getLastName());
                    tratamiento.setPacienteTipoDocumento(paciente.getDocumentType()); 
                    tratamiento.setPacienteNumero(paciente.getDocumentNumber());
                    tratamiento.setMedicoNombre(
                            medico.getFirstName() + " " + medico.getLastName());
                    tratamiento.setEspecialidadNombre(especialidad.getName());

                    tratamiento.setItems(itemsConPrecio);

                    double total = itemsConPrecio.stream()
                            .mapToDouble(TratamientoItem::getSubtotal)
                            .sum();
                    tratamiento.setTotal(total);

                    // Genera ticket correlativo (reactivo) antes de guardar
                    return repository.countAll()
                            .flatMap(count -> {
                                String fecha = LocalDate.now()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                String sufijo = String.format("%05d", count + 1);
                                String ticket = "TRAT-" + fecha + "-" + sufijo;

                                tratamiento.setTicket(ticket);
                                tratamiento.setFecha(LocalDateTime.now());
                                tratamiento.setEstado("consignado");
                                tratamiento.setCreadoEn(LocalDateTime.now());
                                tratamiento.setActualizadoEn(LocalDateTime.now());

                                return repository.save(tratamiento);
                            });
                });
    }

}