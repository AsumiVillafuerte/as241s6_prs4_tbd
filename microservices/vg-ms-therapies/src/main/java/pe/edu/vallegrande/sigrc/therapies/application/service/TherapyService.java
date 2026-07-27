package pe.edu.vallegrande.sigrc.therapies.application.service;

import pe.edu.vallegrande.sigrc.therapies.domain.model.Therapy;
import pe.edu.vallegrande.sigrc.therapies.domain.port.output.TherapyRepository;
import pe.edu.vallegrande.sigrc.therapies.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class TherapyService {

    private final TherapyRepository repository;
    private final TicketService ticketService;

    public TherapyService(TherapyRepository repository, TicketService ticketService) {
        this.repository = repository;
        this.ticketService = ticketService;
    }

    public Flux<Therapy> findAll() {
        return repository.findAll();
    }

    public Flux<Therapy> findAllPaged(int page, int size) {
        return repository.findAllPaged(page, size);
    }

    public Mono<Long> countAll() {
        return repository.countAll();
    }

    public Mono<Therapy> findById(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Therapy not found: " + id)));
    }

    public Mono<Therapy> create(Therapy therapy) {
        therapy.setStatus("CONSIGNADO");
        therapy.setCreatedAt(LocalDateTime.now());
        therapy.setUpdatedAt(LocalDateTime.now());
        return ticketService.generateTicket()
            .flatMap(ticket -> {
                therapy.setTicket(ticket);
                return repository.save(therapy);
            });
    }

    public Mono<Therapy> update(String id, Therapy therapy) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Therapy not found: " + id)))
            .flatMap(existing -> {
                existing.setSpecialtyId(therapy.getSpecialtyId());
                existing.setClientType(therapy.getClientType());
                existing.setMedic(therapy.getMedic());
                existing.setMedicName(therapy.getMedicName());
                existing.setPatient(therapy.getPatient());
                existing.setPatientName(therapy.getPatientName());
                existing.setDni(therapy.getDni());
                existing.setRegisterBy(therapy.getRegisterBy());
                existing.setRegisterByName(therapy.getRegisterByName());
                existing.setTotal(therapy.getTotal());
                existing.setType(therapy.getType());
                existing.setTarjeta(therapy.getTarjeta());
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Therapy not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("ANULADO");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing).then(Mono.empty());
            });
    }

    public Mono<Therapy> deactivate(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Therapy not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("ANULADO");
                existing.setDeletedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }

    public Mono<Therapy> restore(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Therapy not found: " + id)))
            .flatMap(existing -> {
                existing.setStatus("CONSIGNADO");
                existing.setDeletedAt(null);
                existing.setUpdatedAt(LocalDateTime.now());
                return repository.save(existing);
            });
    }
}
