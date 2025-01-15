package org.example.uzgotuje.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class for setting up the password encoder bean.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates and configures a {@link BCryptPasswordEncoder} bean.
     *
     * @return a configured {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
