package com.Notifica.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Pega a autenticação atual do contexto de segurança do Spring
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se a autenticação é válida e se o principal é um JWT
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof Jwt)) {
            // Se não houver usuário logado, retorna um Optional vazio.
            // Você pode retornar um valor padrão como "system" se preferir.
            return Optional.of("system");
        }

        // Extrai o 'preferred_username' do token JWT.
        // O Keycloak/OAuth2 geralmente usa este claim para o nome de usuário.
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Optional.ofNullable(jwt.getClaimAsString("preferred_username"));
    }
}