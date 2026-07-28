package pe.edu.vallegrade.sigrc.treatments.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pe.edu.vallegrade.sigrc.treatments.application.mappers.TratamientoMapper;
import pe.edu.vallegrade.sigrc.treatments.application.usecase.ChangeEstadoUseCaseImpl;
import pe.edu.vallegrade.sigrc.treatments.application.usecase.ChangeTipoUseCaseImpl;
import pe.edu.vallegrade.sigrc.treatments.application.usecase.CreateTratamientoUseCaseImpl;
import pe.edu.vallegrade.sigrc.treatments.application.usecase.GetTratamientoUseCaseImpl;
import pe.edu.vallegrade.sigrc.treatments.application.usecase.UpdateTratamientoUseCaseImpl;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IChangeEstadoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IChangeTipoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.ICreateTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IGetTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.in.IUpdateTratamientoUseCase;
import pe.edu.vallegrade.sigrc.treatments.domain.ports.out.ITratamientoRepository;
import pe.edu.vallegrade.sigrc.treatments.infrastructure.adapters.out.webclient.MaestrosWebClient;

@Configuration
public class BeanConfiguration {
    @Bean
    public TratamientoMapper tratamientoMapper() {
        return new TratamientoMapper();
    }

    @Bean
    public ICreateTratamientoUseCase createTratamientoUseCase(
            ITratamientoRepository repository,
            MaestrosWebClient maestrosWebClient) {
        return new CreateTratamientoUseCaseImpl(repository, maestrosWebClient);
    }

    @Bean
    public IGetTratamientoUseCase getTratamientoUseCase(ITratamientoRepository repository) {
        return new GetTratamientoUseCaseImpl(repository);
    }

    @Bean
    public IUpdateTratamientoUseCase updateTratamientoUseCase(
            ITratamientoRepository repository,
            MaestrosWebClient maestrosWebClient) {
        return new UpdateTratamientoUseCaseImpl(repository, maestrosWebClient);
    }

    @Bean
    public IChangeEstadoUseCase changeEstadoUseCase(ITratamientoRepository repository) {
        return new ChangeEstadoUseCaseImpl(repository);
    }

    @Bean
    public IChangeTipoUseCase changeTipoUseCase(ITratamientoRepository repository) {
        return new ChangeTipoUseCaseImpl(repository);
    }
}
