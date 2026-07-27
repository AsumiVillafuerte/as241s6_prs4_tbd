package pe.edu.vallegrande.sigrc.specialties.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.vallegrande.sigrc.specialties.application.service.ClientTypeApplicationService;
import pe.edu.vallegrande.sigrc.specialties.application.service.SpecialtyApplicationService;
import pe.edu.vallegrande.sigrc.specialties.application.service.TreatmentApplicationService;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.ClientTypeRepositoryPort;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.SpecialtyRepositoryPort;
import pe.edu.vallegrande.sigrc.specialties.domain.port.output.TreatmentRepositoryPort;

@Configuration
public class BeanConfiguration {

    @Bean
    public SpecialtyApplicationService specialtyApplicationService(SpecialtyRepositoryPort specialtyRepositoryPort) {
        return new SpecialtyApplicationService(specialtyRepositoryPort);
    }

    @Bean
    public TreatmentApplicationService treatmentApplicationService(TreatmentRepositoryPort treatmentRepositoryPort) {
        return new TreatmentApplicationService(treatmentRepositoryPort);
    }

    @Bean
    public ClientTypeApplicationService clientTypeApplicationService(ClientTypeRepositoryPort clientTypeRepositoryPort) {
        return new ClientTypeApplicationService(clientTypeRepositoryPort);
    }
}
