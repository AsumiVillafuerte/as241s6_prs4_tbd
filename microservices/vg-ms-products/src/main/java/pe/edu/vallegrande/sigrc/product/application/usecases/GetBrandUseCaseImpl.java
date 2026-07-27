package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.application.dto.response.BrandResponse;
import pe.edu.vallegrande.sigrc.product.application.mappers.BrandMapper;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IGetBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IBrandRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBrandUseCaseImpl implements IGetBrandUseCase {

    private final IBrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Mono<BrandResponse> getBrandById(UUID id) {
        log.debug("Buscando marca con ID: {}", id);
        return brandRepository.findById(id)
                .doOnNext(brand -> log.debug("Marca encontrada: {}", brand))
                .switchIfEmpty(Mono.error(new NotFoundException("Marca no encontrada con ID: " + id)))
                .map(brandMapper::toResponse);
    }

    @Override
    public Flux<BrandResponse> getAllBrands() {
        log.debug("Listando todas las marcas");
        return brandRepository.findAll()
                .doOnNext(brand -> log.debug("Marca: {}", brand))
                .map(brandMapper::toResponse)
                .doOnComplete(() -> log.debug("Listado de marcas completado"));
    }

    @Override
    public Flux<BrandResponse> getBrandsByStatus(Character status) {
        log.debug("Listando marcas con estado: {}", status);
        return brandRepository.findByStatus(status)
                .doOnNext(brand -> log.debug("Marca con estado {}: {}", status, brand))
                .map(brandMapper::toResponse)
                .doOnComplete(() -> log.debug("Listado de marcas por estado completado"));
    }
}
