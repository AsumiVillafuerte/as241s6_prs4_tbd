package pe.edu.vallegrande.sigrc.specialties.infrastructure.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClientTypeRequest(
    @NotBlank(message = "El nombre es obligatorio") 
    String name,
    
    @NotBlank(message = "La descripción es obligatoria") 
    String description
) {}
