package org.example.uzgotuje.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for application-specific beans.
 */
@Configuration
public class AppConfig {

    /**
     * Creates and configures a {@link RestTemplate} bean.
     *
     * @param builder the {@link RestTemplateBuilder} used to build the {@link RestTemplate}
     * @return a configured {@link RestTemplate} instance
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}