package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.clients;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserClientResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String username;
    private List<String> roles;
    private String status;
}
