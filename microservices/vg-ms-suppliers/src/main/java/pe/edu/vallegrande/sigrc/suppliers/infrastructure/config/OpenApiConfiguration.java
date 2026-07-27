package pe.edu.vallegrande.sigrc.suppliers.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ms-suppliers-service API")
                        .version("1.0.0")
                        .description("Microservicio de gestión de proveedores — Cáritas Yauyos-Cañete. " +
                                "Gestiona el catálogo maestro de proveedores con operaciones de " +
                                "registro, consulta, actualización y desactivación lógica.")
                        .contact(new Contact()
                                .name("Valle Grande — AS241")
                                .email("marilyn.vilcapuma.trujillo@vallegrande.edu.pe")));
    }
}
