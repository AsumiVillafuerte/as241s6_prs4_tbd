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
public class ClientType {
    private String id;
    private String name;
    private String description;
    private CommonStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void initializeForCreation() {
        this.status = CommonStatus.ACTIVE;
        this.name = this.name != null ? this.name.toUpperCase().trim() : "";
        this.description = this.description != null ? this.description.trim() : "";
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

    public void updateInfo(String name, String description) {
        if (name != null && !name.isBlank()) {
            this.name = name.toUpperCase().trim();
        }
        if (description != null) {
            this.description = description.trim();
        }
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
