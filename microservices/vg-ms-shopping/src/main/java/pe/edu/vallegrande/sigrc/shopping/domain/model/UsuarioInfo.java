package pe.edu.vallegrande.sigrc.shopping.domain.model;

public record UsuarioInfo(
        String userId,
        String firstName,
        String lastName,
        String role
) {
    public String nombreCompleto() {
        return firstName + " " + lastName;
    }
}
