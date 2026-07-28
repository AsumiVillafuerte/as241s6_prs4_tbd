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
public class Consultation {
    private String id;
    private String ticket;
    private String specialtyId;
    private String medic;
    private String medicName;
    private String patient;
    private String patientName;
    private String dni;
    private String registerBy;
    private String registerByName;
    private BigDecimal total;
    private String tipo;
    private String tarjeta;
    private String status;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public void initializeForCreation() {
        this.status = "CONSIGNADO";
        this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
        this.updatedAt = this.createdAt;
    }

    public void assignTicket(String ticket) {
        this.ticket = ticket;
    }

    public void updateConsultationInfo(String specialtyId, String medic, String medicName,
                                       String patient, String patientName, String dni,
                                       String registerBy, String registerByName,
                                       BigDecimal total, String tipo, String tarjeta) {
        if (specialtyId != null) this.specialtyId = specialtyId;
        if (medic != null) this.medic = medic;
        if (medicName != null) this.medicName = medicName;
        if (patient != null) this.patient = patient;
        if (patientName != null) this.patientName = patientName;
        if (dni != null) this.dni = dni;
        if (registerBy != null) this.registerBy = registerBy;
        if (registerByName != null) this.registerByName = registerByName;
        if (total != null) this.total = total;
        if (tipo != null) this.tipo = tipo;
        if (tarjeta != null) this.tarjeta = tarjeta;
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void cancel() {
        this.status = "ANULADO";
        this.deletedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void restore() {
        this.status = "CONSIGNADO";
        this.deletedAt = null;
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
