package pe.edu.vallegrande.sigrc.specialties.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Treatment {
    private String id;
    private String code;
    private String name;
    private String specialtyId; 
    private CommonStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Inicialización limpia de negocio al crear un Tratamiento
    public void initializeForCreation() {
        this.status = CommonStatus.ACTIVE;
        this.name = this.name != null ? this.name.toUpperCase().trim() : "";
        this.code = this.code != null ? this.code.toUpperCase().trim() : "";
        this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
        this.updatedAt = this.createdAt;
    }

    public void enable() {
        this.status = CommonStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void disable() {
        this.status = CommonStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void updateInfo(String name, String specialtyId) {
        if (name != null && !name.isBlank()) {
            this.name = name.toUpperCase().trim();
        }
        if (specialtyId != null && !specialtyId.isBlank()) {
            this.specialtyId = specialtyId.trim();
        }
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
