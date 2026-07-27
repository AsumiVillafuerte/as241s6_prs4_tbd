package pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.sigrc.shopping.infrastructure.adapter.out.persistence.entity.DetalleCompraMedicamentoEntity;
import reactor.core.publisher.Flux;

public interface DetalleCompraR2dbcRepository extends ReactiveCrudRepository<DetalleCompraMedicamentoEntity, Long> {

    Flux<DetalleCompraMedicamentoEntity> findByCompraId(Long compraId);
}
