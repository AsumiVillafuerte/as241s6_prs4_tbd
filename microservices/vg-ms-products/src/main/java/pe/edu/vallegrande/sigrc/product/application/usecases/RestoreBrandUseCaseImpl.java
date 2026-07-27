package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IRestoreBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IBrandRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestoreBrandUseCaseImpl implements IRestoreBrandUseCase {

    private final IBrandRepository brandRepository;

    @Override
    public Mono<Void> restoreBrand(UUID id) {
        return brandRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Marca no encontrada con ID: " + id)))
                .flatMap(brand -> {
                    brand.setStatus('A');
                    return brandRepository.update(brand);
                })
                .then();
    }
}
