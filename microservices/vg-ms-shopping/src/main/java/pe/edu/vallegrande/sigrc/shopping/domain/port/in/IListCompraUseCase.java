package pe.edu.vallegrande.sigrc.shopping.domain.port.in;

import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;
import reactor.core.publisher.Flux;

public interface IListCompraUseCase {

    Flux<Compra> execute();
}
