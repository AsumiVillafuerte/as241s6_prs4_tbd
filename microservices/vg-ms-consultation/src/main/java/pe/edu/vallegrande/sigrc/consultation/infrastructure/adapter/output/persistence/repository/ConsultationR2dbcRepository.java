package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity.ConsultationEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ConsultationR2dbcRepository extends R2dbcRepository<ConsultationEntity, String> {
    Mono<ConsultationEntity> findByTicket(String ticket);
}
