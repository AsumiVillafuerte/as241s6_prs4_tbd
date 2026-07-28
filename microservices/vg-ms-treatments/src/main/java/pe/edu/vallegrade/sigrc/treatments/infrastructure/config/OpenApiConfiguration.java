package pe.edu.vallegrade.sigrc.treatments.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {
     @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Tratamientos - Sis Cáritas")
                        .version("1.0.0")
                        .description("Microservicio para gestión de tratamientos médicos")
                        .contact(new Contact()
                                .name("Valle Grande")
                                .email("sigrc@vallegrade.edu.pe")));
    }
}
