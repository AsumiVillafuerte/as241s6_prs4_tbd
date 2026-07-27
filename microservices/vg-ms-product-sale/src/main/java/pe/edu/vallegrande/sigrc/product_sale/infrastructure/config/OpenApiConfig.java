package pe.edu.vallegrande.sigrc.product_sale.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:9098}")
    private String serverPort;

    @Bean
    public OpenAPI productSaleOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Servidor de desarrollo");

        Contact contact = new Contact();
        contact.setName("Valle Grande");
        contact.setEmail("soporte@vallegrande.edu.pe");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("API de Ventas de Productos - Microservicio")
                .version("1.0.0")
                .contact(contact)
                .description("API REST para la gestión de ventas de productos del sistema SIGRC")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("ventas")
                .pathsToMatch("/api/**")
                .pathsToExclude("/actuator/**")
                .build();
    }
}
