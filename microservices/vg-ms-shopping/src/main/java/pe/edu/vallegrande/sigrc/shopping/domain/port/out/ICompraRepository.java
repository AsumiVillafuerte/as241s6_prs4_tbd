package pe.edu.vallegrande.sigrc.shopping.domain.port.out;

import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICompraRepository {

    Mono<Compra> save(Compra compra);

    Mono<Compra> findById(Long id);

    Mono<Boolean> existsByNumeroComprobante(String numeroComprobante);

    Flux<Compra> findAll();

    Mono<Compra> update(Compra compra);
}
