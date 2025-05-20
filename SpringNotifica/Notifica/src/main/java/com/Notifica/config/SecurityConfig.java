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

import org.springframework.http.HttpMethod;

import java.util.*;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // habilita CORS com nossa configuração
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // libera o preflight OPTIONS em todas as rotas
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // login fica aberto
                .requestMatchers("/login").permitAll()
                // só admins podem criar novos usuários
                .requestMatchers("/novo-usuario/save").hasRole("admin")
                // só admins podem as operações críticas em tickets
                .requestMatchers(
                    "/tickets/deletar/**",
                    "/tickets/iniciar/**",
                    "/tickets/finalizar/**",
                    "/tickets/voltarAberto/**"
                ).hasRole("admin")
                // imagens e public continuam abertas
                .requestMatchers("/image/**", "/public/**").permitAll()
                // todo o resto exige autenticação
                .anyRequest().authenticated()
            )
            // configura o Resource Server para usar nosso conversor customizado de roles
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(customJwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    /**
     * Lê o claim "resource_access" → "springboot-client" → "roles",
     * extrai cada role e adiciona prefixo "ROLE_".
     */
    private JwtAuthenticationConverter customJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess != null) {
                Object clientNode = resourceAccess.get("springboot-client");
                if (clientNode instanceof Map<?, ?>) {
                    Object rolesObj = ((Map<?, ?>) clientNode).get("roles");
                    if (rolesObj instanceof Collection<?>) {
                        for (Object role : (Collection<?>) rolesObj) {
                            String r = role.toString();
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
     * - libera localhost:4200 e 192.168.3.101 para chamadas AJAX
     * - permite métodos comuns e headers de autenticação
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
config.setAllowedOrigins(Arrays.asList(
        "http://localhost:4200",
        "http://192.168.3.101",
        "https://192.168.3.101"
));

        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));
        config.setAllowedHeaders(Arrays.asList(
            "Authorization", "Content-Type"
        ));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
