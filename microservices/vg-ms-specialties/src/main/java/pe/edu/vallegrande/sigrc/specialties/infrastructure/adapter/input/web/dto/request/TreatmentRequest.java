package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TreatmentRequest(
    @NotBlank(message = "El código es obligatorio")
    @Size(min = 2, max = 10, message = "El código debe tener entre 2 y 10 caracteres")
    String code,
    
    @NotBlank(message = "El nombre es obligatorio") 
    String name,
    
    @NotBlank(message = "La especialidad es obligatoria") 
    String specialtyId
) {}
