package pe.edu.vallegrade.sigrc.treatments.application.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pe.edu.vallegrade.sigrc.treatments.domain.models.Tratamiento;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComprobanteService {

    private final ITratamientoRepository repository;

    public Mono<byte[]> generarComprobante(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                    new RuntimeException("Tratamiento no encontrado con id: " + id)
                ))
                .flatMap(tratamiento ->
                    Mono.fromCallable(() -> procesarReporte(tratamiento))
                        .subscribeOn(Schedulers.boundedElastic())
                );
    }

    private byte[] procesarReporte(Tratamiento t) throws Exception {

        // Carga el .jasper compilado desde resources
        InputStream jasperStream = new ClassPathResource("reports/comprobante-tratamiento.jasper")
                .getInputStream();

        // Parámetros — datos del encabezado del comprobante
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Map<String, Object> params = new HashMap<>();
        params.put("PACIENTE",        nvl(t.getPacienteNombre()));
        params.put("TIPO_DOCUMENTO",  nvl(t.getPacienteTipoDocumento()));
        params.put("NDOCUMENTO",      nvl(t.getPacienteNumero()));
        params.put("MEDICO",          nvl(t.getMedicoNombre()));
        params.put("ESPECIALIDAD",    nvl(t.getEspecialidadNombre()));
        params.put("TICKET",          nvl(t.getTicket()));
        params.put("FECHA",           t.getFecha() != null
                                        ? t.getFecha().format(fmt) : "");
        params.put("ESTADO",          nvl(t.getEstado()).toUpperCase());
        params.put("TOTAL",           String.format("%.2f", t.getTotal()));
        params.put("METODOPAGO",      nvl(t.getMetodoPago()).toUpperCase());
        params.put("TIPO",            nvl(t.getTipo()).toUpperCase());

        // Datasource — lista de items para la tabla dinámica
        List<ItemReporte> itemsReporte = t.getItems().stream()
                .map(item -> new ItemReporte(
                        nvl(item.getNombre()),
                        String.format("%.2f", item.getPrecioUnitario()),
                        String.format("%.2f", item.getSubtotal())
                ))
                .collect(Collectors.toList());

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(itemsReporte);

        // Genera el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperStream, params, dataSource
        );

        // Exporta a PDF en bytes
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private String nvl(String val) {
        return val != null ? val : "";
    }

    // DTO interno para los items del reporte
    public static class ItemReporte {
        private String nombre;
        private String precio;
        private String subtotal;

        public ItemReporte(String nombre, String precio, String subtotal) {
            this.nombre = nombre;
            this.precio = precio;
            this.subtotal = subtotal;
        }

        public String getNombre()   { return nombre; }
        public String getPrecio()   { return precio; }
        public String getSubtotal() { return subtotal; }
    }
}