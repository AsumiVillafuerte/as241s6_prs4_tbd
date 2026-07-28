package pe.edu.vallegrande.sigrc.consultation.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.vallegrande.sigrc.consultation.application.service.ConsultationApplicationService;
import pe.edu.vallegrande.sigrc.consultation.application.service.ConsultationPriceApplicationService;
import pe.edu.vallegrande.sigrc.consultation.application.service.TicketApplicationService;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.ConsultationUseCase;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.ConsultationPriceUseCase;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.TicketSequenceUseCase;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.ConsultationPriceRepositoryPort;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.ConsultationRepositoryPort;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.TicketSequenceRepositoryPort;

@Configuration
public class BeanConfiguration {

    @Bean
    public ConsultationUseCase consultationUseCase(ConsultationRepositoryPort repositoryPort, TicketSequenceUseCase ticketService) {
        return new ConsultationApplicationService(repositoryPort, ticketService);
    }

    @Bean
    public ConsultationPriceUseCase consultationPriceUseCase(ConsultationPriceRepositoryPort repositoryPort) {
        return new ConsultationPriceApplicationService(repositoryPort);
    }

    @Bean
    public TicketSequenceUseCase ticketSequenceUseCase(TicketSequenceRepositoryPort repositoryPort) {
        return new TicketApplicationService(repositoryPort);
    }
}
