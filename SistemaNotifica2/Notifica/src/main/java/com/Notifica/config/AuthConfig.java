package com.Notifica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class AuthConfig {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.disable())  // Cors gerenciado manualmente com o filtro CORS
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login").permitAll()  // Permite acesso ao login sem autenticação
                        .requestMatchers("/novo-usuario/save").hasAnyRole("admin")  // Exige autorização de administrador
                        .requestMatchers("/tickets/deletar/**").hasAnyRole("admin")  // Exige autorização de administrador
                        .requestMatchers("/tickets/iniciar/**").hasAnyRole("admin")  // Exige autorização de administrador
                        .requestMatchers("/tickets/finalizar/**").hasAnyRole("admin")  // Exige autorização de administrador
                        .requestMatchers("/tickets/voltarAberto/**").hasAnyRole("admin")  // Exige autorização de administrador
                        .requestMatchers("/image/download/**").permitAll()  // Permite acesso às imagens sem autenticação
                        .anyRequest().authenticated())  // Exige autenticação para todas as outras requisições
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // Adiciona o filtro JWT antes do filtro padrão
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // Sem estado (sem sessões)

        return http.build();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT));
        config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
        config.setMaxAge(3600L); // Configura o tempo de vida do cache CORS
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(-102); // Garante que o filtro CORS seja aplicado antes do filtro de autenticação
        return bean;
    }
}
