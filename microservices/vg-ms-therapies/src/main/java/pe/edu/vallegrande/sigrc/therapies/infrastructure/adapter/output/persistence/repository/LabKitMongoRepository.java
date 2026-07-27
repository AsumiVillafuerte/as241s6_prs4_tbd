package pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.repository;

import pe.edu.vallegrande.sigrc.therapies.infrastructure.adapter.output.persistence.entity.LabKitDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LabKitMongoRepository extends ReactiveMongoRepository<LabKitDocument, String> {
}
