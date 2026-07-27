package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.application.dto.request.CreateBrandRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.BrandResponse;
import pe.edu.vallegrande.sigrc.product.application.mappers.BrandMapper;
import pe.edu.vallegrande.sigrc.product.domain.models.Brand;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.ICreateBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IBrandRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBrandUseCaseImpl implements ICreateBrandUseCase {

    private final IBrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Mono<BrandResponse> createBrand(CreateBrandRequest request) {
        log.debug("Creando marca: {}", request);
        Brand brand = brandMapper.toDomain(request);
        return brandRepository.save(brand)
                .doOnNext(saved -> log.debug("Marca creada con ID: {}", saved.getId()))
                .map(brandMapper::toResponse);
    }
}
