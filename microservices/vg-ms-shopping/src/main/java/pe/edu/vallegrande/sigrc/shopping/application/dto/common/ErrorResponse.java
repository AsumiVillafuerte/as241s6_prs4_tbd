package pe.edu.vallegrande.sigrc.shopping.application.dto.common;

public record ErrorResponse(
        boolean success,
        String message,
        String error
) {
    public static ErrorResponse of(String message, String error) {
        return new ErrorResponse(false, message, error);
    }
}
