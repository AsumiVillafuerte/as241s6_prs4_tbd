package pe.edu.vallegrande.sigrc.product_sale.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sales", schema = "public")
public class SaleEntity {

    @Id
    private UUID id;

    @Column("ticket_number")
    private String ticketNumber;

    @Column("patient_id")
    private UUID patientId;

    @Column("user_id")
    private String userId;

    @Column("total")
    private BigDecimal total;

    @Column("sale_type")
    private String saleType;

    @Column("status")
    private String status;

    @Column("sale_date")
    private LocalDate saleDate;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
