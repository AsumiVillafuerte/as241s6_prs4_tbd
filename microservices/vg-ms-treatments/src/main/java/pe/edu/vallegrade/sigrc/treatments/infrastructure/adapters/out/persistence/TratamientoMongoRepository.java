package pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface TratamientoMongoRepository extends ReactiveMongoRepository<TratamientoDocument, String>{
    
    Flux<TratamientoDocument> findAllByOrderByFechaDesc();
    Flux<TratamientoDocument> findByEstado(String estado);
    Flux<TratamientoDocument> findByPacienteId(String pacienteId);
    Flux<TratamientoDocument> findByMedicoId(String medicoId);
    Flux<TratamientoDocument> findByEspecialidadId(String especialidadId);
}
