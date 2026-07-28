package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity.ConsultationPriceEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ConsultationPriceR2dbcRepository extends R2dbcRepository<ConsultationPriceEntity, String> {
    Flux<ConsultationPriceEntity> findBySpecialtyId(String specialtyId);
}
