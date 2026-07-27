package pe.edu.vallegrande.sigrc.product.domain.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class PeruDateTime {

    public static final ZoneId ZONE = ZoneId.of("America/Lima");

    private PeruDateTime() {}

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE);
    }
}
