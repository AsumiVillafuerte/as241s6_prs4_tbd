package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.adapters.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleStatus;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.enums.SaleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "medicine_sales")
public class MedicineSaleDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String ticket;

    private LocalDateTime saleDate;
    private String patientId;
    private String patientName;
    private String dni;
    private String cashierId;
    private String cashierName;
    private BigDecimal total;
    private SaleType type;
    private SaleStatus status;
    private List<SaleItemDocument> items;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
