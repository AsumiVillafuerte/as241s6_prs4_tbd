package pe.edu.vallegrande.sigrc.suppliers.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.vallegrande.sigrc.suppliers.application.usecases.*;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.in.*;
import pe.edu.vallegrande.sigrc.suppliers.domain.ports.out.ISupplierRepository;

@Configuration
public class BeanConfiguration {

    @Bean
    public ICreateSupplierUseCase createSupplierUseCase(ISupplierRepository supplierRepository) {
        return new CreateSupplierUseCaseImpl(supplierRepository);
    }

    @Bean
    public IGetSupplierUseCase getSupplierUseCase(ISupplierRepository supplierRepository) {
        return new GetSupplierUseCaseImpl(supplierRepository);
    }

    @Bean
    public IUpdateSupplierUseCase updateSupplierUseCase(ISupplierRepository supplierRepository) {
        return new UpdateSupplierUseCaseImpl(supplierRepository);
    }

    @Bean
    public IDeleteSupplierUseCase deleteSupplierUseCase(ISupplierRepository supplierRepository) {
        return new DeleteSupplierUseCaseImpl(supplierRepository);
    }
}
