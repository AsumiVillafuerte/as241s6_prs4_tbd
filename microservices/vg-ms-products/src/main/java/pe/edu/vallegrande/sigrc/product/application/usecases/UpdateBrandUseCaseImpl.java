package pe.edu.vallegrande.sigrc.product.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.sigrc.product.application.dto.request.UpdateBrandRequest;
import pe.edu.vallegrande.sigrc.product.application.dto.response.BrandResponse;
import pe.edu.vallegrande.sigrc.product.application.mappers.BrandMapper;
import pe.edu.vallegrande.sigrc.product.domain.exceptions.NotFoundException;
import pe.edu.vallegrande.sigrc.product.domain.models.Brand;
import pe.edu.vallegrande.sigrc.product.domain.ports.in.IUpdateBrandUseCase;
import pe.edu.vallegrande.sigrc.product.domain.ports.out.IBrandRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateBrandUseCaseImpl implements IUpdateBrandUseCase {

    private final IBrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Mono<BrandResponse> updateBrand(UUID id, UpdateBrandRequest request) {
        return brandRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Marca no encontrada con ID: " + id)))
                .map(existing -> {
                    Brand updated = brandMapper.mapUpdateToDomain(request);
                    updated.setId(existing.getId());
                    updated.setStatus(existing.getStatus());
                    if (updated.getName() == null) updated.setName(existing.getName());
                    return updated;
                })
                .flatMap(brandRepository::update)
                .map(brandMapper::toResponse);
    }
}
