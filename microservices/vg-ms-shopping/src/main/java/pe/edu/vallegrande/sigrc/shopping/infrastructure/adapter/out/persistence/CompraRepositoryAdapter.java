package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.shopping.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;
import pe.edu.vallegrande.sigrc.shopping.domain.model.EstadoCompra;
import pe.edu.vallegrande.sigrc.shopping.domain.model.TipoCompra;
import pe.edu.vallegrande.sigrc.shopping.domain.port.out.ICompraRepository;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.entity.CompraEntity;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.repository.CompraR2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CompraRepositoryAdapter implements ICompraRepository {

    private final CompraR2dbcRepository r2dbcRepository;

    @Override
    public Mono<Compra> save(Compra compra) {
        return Mono.defer(() -> {
            CompraEntity entity = toEntity(compra);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            return r2dbcRepository.save(entity);
        }).map(this::toDomain);
    }

    @Override
    public Mono<Compra> findById(Long id) {
        return r2dbcRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Compra no encontrada con id: " + id)))
                .map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByNumeroComprobante(String numeroComprobante) {
        return r2dbcRepository.existsByNumeroComprobante(numeroComprobante);
    }

    @Override
    public Flux<Compra> findAll() {
        return r2dbcRepository.findAllOrderByFechaCompraDesc().map(this::toDomain);
    }

    @Override
    public Mono<Compra> update(Compra compra) {
        CompraEntity entity = toEntity(compra);
        entity.setUpdatedAt(LocalDateTime.now());
        return r2dbcRepository.save(entity).map(this::toDomain);
    }

    private CompraEntity toEntity(Compra compra) {
        return CompraEntity.builder()
                .id(compra.getId())
                .numeroComprobante(compra.getNumeroComprobante())
                .proveedorId(compra.getProveedorId())
                .usuarioId(compra.getUsuarioId())
                .tipo(compra.getTipo() != null ? compra.getTipo().name() : TipoCompra.COMPRADO.name())
                .estado(compra.getEstado() != null ? compra.getEstado().name() : EstadoCompra.CONSIGNADO.name())
                .precioCompraTotal(compra.getPrecioCompraTotal())
                .precioVentaTotal(compra.getPrecioVentaTotal())
                .fechaCompra(compra.getFechaCompra())
                .createdAt(compra.getCreatedAt())
                .updatedAt(compra.getUpdatedAt())
                .build();
    }

    private Compra toDomain(CompraEntity entity) {
        return Compra.builder()
                .id(entity.getId())
                .numeroComprobante(entity.getNumeroComprobante())
                .proveedorId(entity.getProveedorId())
                .usuarioId(entity.getUsuarioId())
                .tipo(TipoCompra.valueOf(entity.getTipo()))
                .estado(EstadoCompra.valueOf(entity.getEstado()))
                .precioCompraTotal(entity.getPrecioCompraTotal())
                .precioVentaTotal(entity.getPrecioVentaTotal())
                .fechaCompra(entity.getFechaCompra())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
