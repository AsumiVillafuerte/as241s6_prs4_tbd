package pe.edu.vallegrade.sigrc.treatments.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
     @Bean
     public WebClient gatewayWebClient() {
        return WebClient.builder()
                .baseUrl("https://lab.vallegrande.edu.pe/sigrc/gateway")
                .build();
    }
}
