package pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.entities.SaleEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface SaleR2dbcRepository extends R2dbcRepository<SaleEntity, UUID> {
    Flux<SaleEntity> findByStatus(String status);

    @Query("SELECT COUNT(*) FROM sales WHERE sale_date >= :start AND sale_date < :end")
    Mono<Long> countByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COUNT(*) FROM sales")
    Mono<Long> countAll();
}
