package pe.edu.vallegrande.sigrc.shopping.domain.port.out;

import pe.edu.vallegrande.sigrc.shopping.domain.model.UsuarioInfo;
import reactor.core.publisher.Mono;

public interface IUsuarioClient {

    Mono<UsuarioInfo> findById(String usuarioId);
}
