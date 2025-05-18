package com.Notifica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.*;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1) Ativa CORS usando nossa configuração abaixo
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/novo-usuario/save").permitAll()
                .requestMatchers("/novo-usuario/save").hasAnyRole("admin")  // Exige autorização de administrador
                .requestMatchers("/tickets/deletar/**").hasAnyRole("admin")  // Exige autorização de administrador
                .requestMatchers("/tickets/iniciar/**").hasAnyRole("admin")  // Exige autorização de administrador
                .requestMatchers("/tickets/finalizar/**").hasAnyRole("admin")  // Exige autorização de administrador
                .requestMatchers("/tickets/voltarAberto/**").hasAnyRole("admin")  // Exige autorização de administrador
                .requestMatchers("/image/**", "/public/**").permitAll()
                .anyRequest().authenticated()
            )
            // 2) Configura o Resource Server para usar nosso conversor customizado
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(customJwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    /**
     * Converte os roles do Keycloak (dentro de resource_access → springboot-client → roles)
     * em GrantedAuthority com prefixo "ROLE_".
     */
    private JwtAuthenticationConverter customJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

            if (resourceAccess != null) {
                Object clientAccess = resourceAccess.get("springboot-client");
                if (clientAccess instanceof Map<?, ?>) {
                    Object rolesObj = ((Map<?, ?>) clientAccess).get("roles");
                    if (rolesObj instanceof Collection<?>) {
                        for (Object role : (Collection<?>) rolesObj) {
                            String r = role.toString();
                            // Remove possível prefixo duplicado
                            if (r.startsWith("ROLE_")) {
                                r = r.substring(5);
                            }
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + r));
                        }
                    }
                }
            }
            return authorities;
        });

        return converter;
    }

    /**
     * Configuração global de CORS:
     * - libera localhost:4200 para chamadas AJAX 
     * - permite métodos comuns e headers de autenticação
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
