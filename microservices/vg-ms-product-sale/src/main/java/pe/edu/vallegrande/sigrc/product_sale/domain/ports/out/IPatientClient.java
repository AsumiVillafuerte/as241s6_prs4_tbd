package pe.edu.vallegrande.sigrc.product_sale.domain.ports.out;

import pe.edu.vallegrande.sigrc.product_sale.application.dto.response.PatientInfo;
import reactor.core.publisher.Mono;

public interface IPatientClient {
    Mono<PatientInfo> findById(String patientId);
}
