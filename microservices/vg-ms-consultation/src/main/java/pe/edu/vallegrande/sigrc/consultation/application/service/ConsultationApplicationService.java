package pe.edu.vallegrande.sigrc.consultation.application.service;

import pe.edu.vallegrande.sigrc.consultation.domain.exception.NotFoundException;
import pe.edu.vallegrande.sigrc.consultation.domain.model.Consultation;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.ConsultationUseCase;
import pe.edu.vallegrande.sigrc.consultation.domain.port.input.TicketSequenceUseCase;
import pe.edu.vallegrande.sigrc.consultation.domain.port.output.ConsultationRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Supplier;

public class ConsultationApplicationService implements ConsultationUseCase {

    private final ConsultationRepositoryPort repositoryPort;
    private final TicketSequenceUseCase ticketService;

    public ConsultationApplicationService(ConsultationRepositoryPort repositoryPort, TicketSequenceUseCase ticketService) {
        this.repositoryPort = repositoryPort;
        this.ticketService = ticketService;
    }

    @Override
    public Flux<Consultation> getAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Flux<Consultation> getPaged(int page, int size) {
        if (page < 0 || size <= 0) {
            return Flux.error((Supplier<Throwable>) () -> new IllegalArgumentException("Invalid pagination parameters"));
        }
        return repositoryPort.findAllPaged(page, size);
    }

    @Override
    public Mono<Long> count() {
        return repositoryPort.countAll();
    }

    @Override
    public Mono<Consultation> getById(String id) {
        if (id == null || id.isBlank()) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("El ID proporcionado no es v\u00e1lido"));
        }
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error((Supplier<Throwable>) () -> new NotFoundException("Consulta no encontrada con ID: " + id)));
    }

    @Override
    public Mono<Consultation> register(Consultation consultation) {
        if (consultation == null) {
            return Mono.error((Supplier<Throwable>) () -> new IllegalArgumentException("La consulta no puede ser nula"));
        }
        return Mono.defer(() -> {
            consultation.initializeForCreation();
            return ticketService.generateTicket()
                    .flatMap(ticket -> {
                        consultation.assignTicket(ticket);
                        return repositoryPort.save(consultation);
                    });
        });
    }

    @Override
    public Mono<Consultation> modify(String id, Consultation updatedData) {
        return this.getById(id)
                .flatMap(current -> {
                    current.updateConsultationInfo(
                            updatedData.getSpecialtyId(), updatedData.getMedic(), updatedData.getMedicName(),
                            updatedData.getPatient(), updatedData.getPatientName(),
                            updatedData.getDni(), updatedData.getRegisterBy(),
                            updatedData.getRegisterByName(), updatedData.getTotal(),
                            updatedData.getTipo(), updatedData.getTarjeta());
                    return repositoryPort.save(current);
                });
    }

    @Override
    public Mono<Consultation> cancel(String id) {
        return this.getById(id)
                .flatMap(consultation -> {
                    consultation.cancel();
                    return repositoryPort.save(consultation);
                });
    }

    @Override
    public Mono<Consultation> restore(String id) {
        return this.getById(id)
                .flatMap(consultation -> {
                    consultation.restore();
                    return repositoryPort.save(consultation);
                });
    }
}
