package pe.edu.vallegrande.sigrc.shopping.domain.port.in;

import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;
import reactor.core.publisher.Mono;

public interface IGetCompraUseCase {

    Mono<Compra> execute(Long id);
}
