package pe.edu.vallegrande.sigrc.consultation.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories(basePackages = "pe.edu.vallegrande.sigrc.consultation.infrastructure.adapter.output.persistence.repository")
public class R2dbcConfig {
}
