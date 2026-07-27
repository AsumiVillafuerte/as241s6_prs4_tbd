package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LabKitItemRequest {
    private String labTestId;
    private String prueba;
    private String labTestName;
    private BigDecimal labTestPrice;
}
