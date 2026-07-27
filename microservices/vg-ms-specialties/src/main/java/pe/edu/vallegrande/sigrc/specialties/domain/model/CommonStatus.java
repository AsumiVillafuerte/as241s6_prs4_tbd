package pe.edu.vallegrande.sigrc.specialties.domain.model;

public enum CommonStatus {
    ACTIVE, 
    INACTIVE;

    public static CommonStatus fromString(String value) {
        if (value == null) return ACTIVE;
        try {
            return CommonStatus.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            return ACTIVE; // Resiliencia: fallback por defecto
        }
    }
}
