package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper del ApiResponse que devuelve ms-medications.
 * Estructura: { "status": 200, "message": "...", "data": { ... } }
 */
@Data
@NoArgsConstructor
public class MedicationApiResponse {
    private int status;
    private String message;
    private MedicationClientResponse data;
}
