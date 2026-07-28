package pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.entity.TicketSequenceEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface TicketSequenceR2dbcRepository extends R2dbcRepository<TicketSequenceEntity, String> {
    Mono<TicketSequenceEntity> findByDate(String date);
}
