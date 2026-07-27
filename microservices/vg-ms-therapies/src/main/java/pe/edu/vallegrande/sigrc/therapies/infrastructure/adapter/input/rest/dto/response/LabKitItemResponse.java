package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.input.rest.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class LabKitItemResponse {
    private String labTestId;
    private String prueba;
    private String labTestName;
    private BigDecimal labTestPrice;
}
