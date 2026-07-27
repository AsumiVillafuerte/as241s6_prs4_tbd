package pe.edu.vallegrande.sigrc.shopping.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("vg-ms-shopping — Compras de Medicamentos")
                        .description("Microservicio para registrar compras a proveedores y cargar lotes en el inventario de medicamentos (FEFO)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Valle Grande — PRS-4 Cáritas")
                                .email("marilyn.vilcapuma.trujillo@vallegrande.edu.pe")));
    }
}
