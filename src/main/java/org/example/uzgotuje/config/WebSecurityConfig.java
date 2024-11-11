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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserService userService;
    private final PasswordEncoderConfig passwordEncoder;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeRequests(authorizeRequests->authorizeRequests
                        .requestMatchers("/auth/**","/recipe/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(withDefaults());
        return http.build();
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
}
