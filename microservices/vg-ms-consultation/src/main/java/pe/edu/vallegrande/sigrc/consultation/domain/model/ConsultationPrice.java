package pe.edu.vallegrande.sigrc.consultation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationPrice {
    private String id;
    private String specialtyId;
    private BigDecimal price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

    public void initializeForCreation() {
        this.status = "ACTIVE";
        this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
        this.updatedAt = this.createdAt;
    }

    public void updateInfo(String specialtyId, BigDecimal price) {
        if (specialtyId != null) this.specialtyId = specialtyId;
        if (price != null) this.price = price;
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void activate() {
        this.status = "ACTIVE";
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void deactivate() {
        this.status = "INACTIVE";
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
