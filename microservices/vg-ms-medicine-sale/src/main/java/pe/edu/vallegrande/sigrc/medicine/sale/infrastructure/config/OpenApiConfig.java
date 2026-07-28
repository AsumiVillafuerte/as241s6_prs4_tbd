package pe.edu.vallegrande.sigrc.medicine.sale.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("vg-ms-medicine-sale")
                        .version("1.0.0")
                        .description("""
                                Microservicio transaccional de venta de medicamentos.
                                Proyecto de Responsabilidad Social - Cáritas.
                                
                                **Estados de venta:** VENDIDO | CONSIGNADO | DONADO | REVOCADO
                                
                                **Tipos de venta:** GENERAL | ESPECIAL | EMERGENCIA
                                
                                **Formato de ticket:** FAR-YYYY-MM-DD-XXXXXX
                                """)
                        .contact(new Contact()
                                .name("Instituto Valle Grande")
                                .email("soporte@vallegrande.edu.pe"))
                        .license(new License()
                                .name("Privado - Proyecto académico")));
    }
}
