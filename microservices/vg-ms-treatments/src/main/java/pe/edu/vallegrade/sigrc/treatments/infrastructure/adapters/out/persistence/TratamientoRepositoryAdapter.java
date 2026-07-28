package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.*;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.models.TratamientoItem;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor

public class TratamientoRepositoryAdapter implements ITratamientoRepository {
    private final TratamientoMongoRepository mongoRepository;

    // ── Mappers Document ↔ Domain ──────────────────────────────

    private TratamientoDocument toDocument(Tratamiento domain) {
        return TratamientoDocument.builder()
                .id(domain.getId())
                .ticket(domain.getTicket())
                .fecha(domain.getFecha())
                .especialidadId(domain.getEspecialidadId())
                .especialidadNombre(domain.getEspecialidadNombre())
                .medicoId(domain.getMedicoId())
                .medicoNombre(domain.getMedicoNombre())
                .pacienteId(domain.getPacienteId())
                .pacienteNombre(domain.getPacienteNombre())
                .pacienteTipoDocumento(domain.getPacienteTipoDocumento())
                .pacienteNumero(domain.getPacienteNumero())
                .registradoPor(domain.getRegistradoPor())
                .tipo(domain.getTipo())
                .metodoPago(domain.getMetodoPago())
                .estado(domain.getEstado())
                .total(domain.getTotal())
                .items(domain.getItems().stream()
                        .map(this::toItemDocument)
                        .collect(Collectors.toList()))
                .creadoEn(domain.getCreadoEn())
                .actualizadoEn(domain.getActualizadoEn())
                .build();
    }

    private Tratamiento toDomain(TratamientoDocument doc) {
        return Tratamiento.builder()
                .id(doc.getId())
                .ticket(doc.getTicket())
                .fecha(doc.getFecha())
                .especialidadId(doc.getEspecialidadId())
                .especialidadNombre(doc.getEspecialidadNombre())
                .medicoId(doc.getMedicoId())
                .medicoNombre(doc.getMedicoNombre())
                .pacienteId(doc.getPacienteId())
                .pacienteNombre(doc.getPacienteNombre())
                .pacienteTipoDocumento(doc.getPacienteTipoDocumento())
                .pacienteNumero(doc.getPacienteNumero())
                .registradoPor(doc.getRegistradoPor())
                .tipo(doc.getTipo())
                .metodoPago(doc.getMetodoPago())
                .estado(doc.getEstado())
                .total(doc.getTotal())
                .items(doc.getItems().stream()
                        .map(this::toItemDomain)
                        .collect(Collectors.toList()))
                .creadoEn(doc.getCreadoEn())
                .actualizadoEn(doc.getActualizadoEn())
                .build();
    }

    private TratamientoItemDocument toItemDocument(TratamientoItem item) {
        return TratamientoItemDocument.builder()
                .tratamientoMaestroId(item.getTratamientoMaestroId())
                .nombre(item.getNombre())
                .cantidad(item.getCantidad())
                .precioUnitario(item.getPrecioUnitario())
                .subtotal(item.getSubtotal())
                .build();
    }

    private TratamientoItem toItemDomain(TratamientoItemDocument doc) {
        return TratamientoItem.builder()
                .tratamientoMaestroId(doc.getTratamientoMaestroId())
                .nombre(doc.getNombre())
                .cantidad(doc.getCantidad())
                .precioUnitario(doc.getPrecioUnitario())
                .subtotal(doc.getSubtotal())
                .build();
    }

    // ── Implementación del puerto ──────────────────────────────

    @Override
    public Mono<Tratamiento> save(Tratamiento tratamiento) {
        return mongoRepository.save(toDocument(tratamiento))
                .map(this::toDomain);
    }

    @Override
    public Mono<Tratamiento> findById(String id) {
        return mongoRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Flux<Tratamiento> findAll() {
        return mongoRepository.findAllByOrderByFechaDesc()
                .map(this::toDomain);
    }

    @Override
    public Mono<Tratamiento> update(String id, Tratamiento tratamiento) {
        return mongoRepository.findById(id)
                .flatMap(existing -> {
                    // Conserva campos que no se editan
                    existing.setMedicoId(tratamiento.getMedicoId());
                    existing.setMedicoNombre(tratamiento.getMedicoNombre());
                    existing.setTipo(tratamiento.getTipo());
                    existing.setMetodoPago(tratamiento.getMetodoPago());
                    existing.setItems(tratamiento.getItems().stream()
                            .map(this::toItemDocument)
                            .collect(Collectors.toList()));
                    existing.setTotal(tratamiento.getTotal());
                    existing.setActualizadoEn(tratamiento.getActualizadoEn());
                    return mongoRepository.save(existing);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Tratamiento> changeEstado(String id, String nuevoEstado) {
        return mongoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setEstado(nuevoEstado);
                    existing.setActualizadoEn(LocalDateTime.now());
                    return mongoRepository.save(existing);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Tratamiento> changeTipo(String id, String nuevoTipo) {
        return mongoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setTipo(nuevoTipo);
                    existing.setActualizadoEn(LocalDateTime.now());
                    return mongoRepository.save(existing);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Long> countAll() {
        return mongoRepository.count();
    }
}
