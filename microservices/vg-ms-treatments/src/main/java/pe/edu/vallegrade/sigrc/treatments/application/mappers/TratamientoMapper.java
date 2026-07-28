package pe.edu.vallegrade.sigrc.treatments.application.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pe.edu.vallegrade.sigrc.treatments.application.dto.request.CreateTratamientoRequest;
import pe.edu.vallegrade.sigrc.treatments.application.dto.request.TratamientoItemRequest;
import pe.edu.vallegrade.sigrc.treatments.application.dto.request.UpdateTratamientoRequest;
import pe.edu.vallegrade.sigrc.treatments.application.dto.response.TratamientoItemResponse;
import pe.edu.vallegrade.sigrc.treatments.application.dto.response.TratamientoResponse;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.models.TratamientoItem;

@Component
public class TratamientoMapper {

    // CreateRequest → Domain
    public Tratamiento toDomain(CreateTratamientoRequest request) {
        List<TratamientoItem> items = request.getItems().stream()
            .map(this::toDomainItem)
            .collect(Collectors.toList());

        // ← ya no calcula total aquí, lo hace el UseCase después de resolver precios
        return Tratamiento.builder()
            .especialidadId(request.getEspecialidadId())
            .especialidadNombre(request.getEspecialidadNombre())
            .medicoId(request.getMedicoId())
            .medicoNombre(request.getMedicoNombre())
            .pacienteId(request.getPacienteId())
            .pacienteNombre(request.getPacienteNombre())
            .pacienteTipoDocumento(request.getPacienteTipoDocumento())
            .pacienteNumero(request.getPacienteNumero())
            .registradoPor(request.getRegistradoPor())
            .tipo(request.getTipo())
            .metodoPago(request.getMetodoPago())
            .items(items)
            .build();
    }

    // UpdateRequest → Domain
    public Tratamiento toDomain(UpdateTratamientoRequest request) {
        List<TratamientoItem> items = request.getItems().stream()
            .map(this::toDomainItem)
            .collect(Collectors.toList());

        return Tratamiento.builder()
            .medicoId(request.getMedicoId())
            .medicoNombre(request.getMedicoNombre())
            .tipo(request.getTipo())
            .metodoPago(request.getMetodoPago())
            .items(items)
            .build();
    }

    // Domain → Response
    public TratamientoResponse toResponse(Tratamiento domain) {
        return TratamientoResponse.builder()
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
            .items(domain.getItems() != null ? domain.getItems().stream()
                .map(this::toResponseItem)
                .collect(Collectors.toList()) : List.of())
            .creadoEn(domain.getCreadoEn())
            .actualizadoEn(domain.getActualizadoEn())
            .build();
    }

    // Helper items
    private TratamientoItem toDomainItem(TratamientoItemRequest req) {
        return TratamientoItem.builder()
            .tratamientoMaestroId(req.getTratamientoMaestroId())
            .cantidad(req.getCantidad())
            .build();
    }

    private TratamientoItemResponse toResponseItem(TratamientoItem item) {
        return TratamientoItemResponse.builder()
            .tratamientoMaestroId(item.getTratamientoMaestroId())
            .nombre(item.getNombre())
            .cantidad(item.getCantidad())
            .precioUnitario(item.getPrecioUnitario())
            .subtotal(item.getSubtotal())
            .build();
    }
}
