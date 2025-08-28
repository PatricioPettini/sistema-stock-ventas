package com.patomicroservicios.productos_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(Customizer.withDefaults())   // 👈 Habilita CORS
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 👈 preflight
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }
//@Bean
//public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/producto/**").permitAll() // ✅ Libre acceso
//                    .anyRequest().authenticated()                // 🔒 El resto sigue con seguridad
//            );
//    return http.build();
//}


//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration cors = new CorsConfiguration();
//        // Si servís el front en 5500:
//        cors.setAllowedOriginPatterns(List.of(
//                "http://localhost:*",
//                "http://127.0.0.1:*"
//        ));
//        cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
//        cors.setAllowedHeaders(List.of("Authorization","Content-Type","Origin","Accept"));
//        cors.setExposedHeaders(List.of("Authorization","Content-Type"));
//        cors.setAllowCredentials(false); // si no usás cookies
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", cors);
//        return source;
//    }
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults()) // 👈 HABILITAR CORS (no disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // preflight
                    .anyRequest().permitAll() // o authenticated() según quieras
            );
    return http.build();
}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        // agrega ambos orígenes que usás para servir el HTML
        cors.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));
        cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cors.setAllowedHeaders(List.of("Authorization","Content-Type","Origin","Accept"));
        cors.setExposedHeaders(List.of("Content-Type"));
        cors.setAllowCredentials(false); // no usás cookies
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
//
//
//    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
//
//        return converter;
//    }
//}
//
//@SuppressWarnings("unchecked")
//class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>{
//    @Override
//    public Collection<GrantedAuthority> convert(Jwt jwt) {
//        if (jwt.getClaims() == null){
//            return List.of();
//        }
//
//        final Map<String, List<String>> realmAccess = (Map<String, List<String>>) jwt.getClaims().get("realm_access");
//
//        return realmAccess.get("roles").stream()
//                .map(roleName -> "ROLE_" + roleName)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
}