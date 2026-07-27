package pe.edu.vallegrande.sigrc.shopping.domain.port.out;

import pe.edu.vallegrande.sigrc.shopping.domain.model.ProveedorInfo;
import reactor.core.publisher.Mono;

public interface IProveedorClient {

    Mono<ProveedorInfo> findById(Long proveedorId);
}
