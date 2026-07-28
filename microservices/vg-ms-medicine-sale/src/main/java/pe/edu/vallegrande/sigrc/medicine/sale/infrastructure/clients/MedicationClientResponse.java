package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que mapea la respuesta del microservicio ms-medications.
 * Corresponde al campo "data" dentro del ApiResponse que devuelve ese servicio.
 */
@Data
@NoArgsConstructor
public class MedicationClientResponse {
    private String id;
    private String code;
    @JsonAlias("name")
    private String genericName;
    private String commercialName;
    private String category;
    private String form;
    private Integer stock;
    private Integer minStock;
    private Double salePrice;
    private String batchNumber;
    private String status;
}
