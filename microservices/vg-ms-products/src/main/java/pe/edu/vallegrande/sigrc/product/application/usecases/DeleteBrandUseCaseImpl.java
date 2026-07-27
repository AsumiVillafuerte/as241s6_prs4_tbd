package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IDeleteBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IBrandRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBrandUseCaseImpl implements IDeleteBrandUseCase {

    private final IBrandRepository brandRepository;

    @Override
    public Mono<Void> deleteBrand(UUID id) {
        return brandRepository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new NotFoundException("Marca no encontrada con ID: " + id));
                    }
                    return brandRepository.deleteById(id);
                });
    }

    @Override
    public Mono<Void> logicalDeleteBrand(UUID id) {
        return brandRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Marca no encontrada con ID: " + id)))
                .flatMap(brand -> {
                    brand.setStatus('I');
                    return brandRepository.update(brand);
                })
                .then();
    }
}
