package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity.LabTestDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LabTestMongoRepository extends ReactiveMongoRepository<LabTestDocument, String> {
}
