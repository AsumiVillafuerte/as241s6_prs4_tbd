package pe.edu.vallegrande.sigrc.therapies.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabKitItem {
    private String labTestId;
    private String prueba;
    private String labTestName;
    private BigDecimal labTestPrice;
}
