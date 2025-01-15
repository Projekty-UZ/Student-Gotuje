package org.example.uzgotuje.config;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class for setting up web security in the application.
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;
    private final PasswordEncoderConfig passwordEncoder;

    /**
     * Configures the security filter chain.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return a configured {@link SecurityFilterChain} instance
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/**", "/recipe/**", "files/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(withDefaults());
        return http.build();
    }

    /**
     * Configures the authentication manager builder with a DAO authentication provider.
     *
     * @param auth the {@link AuthenticationManagerBuilder} to modify
     * @throws Exception if an error occurs while configuring the authentication manager builder
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * Creates and configures a {@link DaoAuthenticationProvider} bean.
     *
     * @return a configured {@link DaoAuthenticationProvider} instance
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
}

