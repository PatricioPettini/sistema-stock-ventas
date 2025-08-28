package com.microserviciospato.api_gateway_pato.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/eureka/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2Login(); // 👈 esto es clave para redirigir al login
        return http.build();
    }

}
