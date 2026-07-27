package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.client;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.shopping.domain.model.DetalleCompraMedicamento;
import pe.edu.vallegrande.sigrc.shopping.domain.model.MedicamentoInfo;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.IMedicamentoClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Stub de ms-medications para pruebas locales sin conexión al gateway.
 * Activo solo en perfil "stub". En producción y default usa MedicamentoClientAdapter (@Primary).
 * Rutas reales:
 *   GET    /api/v1/medications/{id}
 *   POST   /api/v1/medications        → retorna el nuevo documento con _id como lote_inventario_id
 *   DELETE /api/v1/medications/{id}   → soft delete (desactiva)
 */
@Component
@Profile("stub")
public class MedicamentoClientStub implements IMedicamentoClient {

    @Override
    public Mono<MedicamentoInfo> findById(String medicamentoId) {
        return Mono.just(new MedicamentoInfo(
                medicamentoId,
                "MED-STUB-001",
                "Paracetamol 500mg (stub)",
                "Paracetamol (stub)",
                "Analgésico",
                "Tabletas x 30",
                10,
                "A1-01",
                "Lab Stub S.A.",
                BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(8.50),
                LocalDate.now().plusYears(2)
        ));
    }

    @Override
    public Mono<String> crearLote(MedicamentoInfo medicamentoInfo, DetalleCompraMedicamento detalle) {
        return Mono.just("stub-lote-" + UUID.randomUUID());
    }

    @Override
    public Mono<Void> eliminarLote(String loteInventarioId) {
        return Mono.empty();
    }
}
