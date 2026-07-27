package pe.edu.vallegrande.sigrc.product_sale.domain.ports.out;

import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.UserInfo;
import reactor.core.publisher.Mono;

public interface IUserClient {
    Mono<UserInfo> findById(String userId);
}
