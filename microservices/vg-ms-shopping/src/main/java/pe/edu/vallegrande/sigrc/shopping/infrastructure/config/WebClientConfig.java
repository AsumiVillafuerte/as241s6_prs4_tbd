package pe.edu.vallegrande.sigrc.shopping.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${services.gateway-url}")
    private String gatewayUrl;

    @Bean
    public WebClient gatewayWebClient() {
        return WebClient.builder()
                .baseUrl(gatewayUrl)
                .build();
    }
}
