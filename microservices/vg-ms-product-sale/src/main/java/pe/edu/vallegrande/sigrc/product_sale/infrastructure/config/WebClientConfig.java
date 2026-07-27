package pe.edu.vallegrande.sigrc.product_sale.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${microservices.patients.url}")
    private String patientsUrl;

    @Value("${microservices.users.url}")
    private String usersUrl;

    @Value("${microservices.products.url}")
    private String productsUrl;

    @Bean("patientsWebClient")
    public WebClient patientsWebClient() {
        return WebClient.builder()
                .baseUrl(patientsUrl)
                .build();
    }

    @Bean("usersWebClient")
    public WebClient usersWebClient() {
        return WebClient.builder()
                .baseUrl(usersUrl)
                .build();
    }

    @Bean("productsWebClient")
    public WebClient productsWebClient() {
        return WebClient.builder()
                .baseUrl(productsUrl)
                .build();
    }
}
