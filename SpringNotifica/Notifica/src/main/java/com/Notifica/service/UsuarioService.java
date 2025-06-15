package com.Notifica.service;

import com.Notifica.controller.login.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Tenta autenticar um usuário no Keycloak usando o fluxo de senha.
     * Trata os erros de forma robusta para evitar o "Erro Interno" genérico.
     */
    public String logar(LoginRequest login) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", login.login());
        form.add("password", login.senha());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        String tokenUrl = issuerUri + "/protocol/openid-connect/token";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

            if (response.getBody() != null && response.getBody().containsKey("access_token")) {
                return (String) response.getBody().get("access_token");
            }
            throw new BadCredentialsException("Não foi possível obter o token de acesso do Keycloak, resposta inesperada.");

        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            System.err.println("Erro de autenticação retornado pelo Keycloak: " + responseBody);

            try {
                Map<String, String> errorMap = objectMapper.readValue(responseBody, Map.class);
                String errorDescription = errorMap.getOrDefault("error_description", "Credenciais inválidas ou conta com problemas.");
                throw new BadCredentialsException(errorDescription, e);
            } catch (JsonProcessingException jsonException) {
                throw new BadCredentialsException("Falha na autenticação. Resposta inválida do servidor: " + responseBody, e);
            }
        }
    }

    /**
     * Cria um novo usuário no Keycloak usando a API de Admin.
     */
    public void saveNewUser(String usuario, String password, Boolean isAdmin) {
        // 1) Obter um token de admin para o nosso client
        String adminToken = getAdminToken();

        // 2) Montar a requisição para criar o usuário
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", password);
        credential.put("temporary", false);

        Map<String, Object> payload = new HashMap<>();
        payload.put("username", usuario);
        payload.put("enabled", true);
        payload.put("credentials", List.of(credential));

        if (isAdmin != null && isAdmin) {
            payload.put("realmRoles", List.of("admin"));
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        
        // Constrói a URL de admin corretamente a partir do issuerUri
        String adminUrl = issuerUri.replaceFirst("/realms/.*", "") + "/admin/realms/Notifica/users";

        try {
            restTemplate.postForEntity(adminUrl, request, Void.class);
        } catch (HttpClientErrorException e) {
            System.err.println("Falha ao criar usuário no Keycloak: " + e.getResponseBodyAsString());
            throw new RuntimeException("Falha ao criar usuário no Keycloak. Verifique os logs e as permissões do client.", e);
        }
    }

    /**
     * Método privado para obter o token de admin do client usando client_credentials.
     */
    private String getAdminToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        String tokenUrl = issuerUri + "/protocol/openid-connect/token";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            if (response.getBody() != null && response.getBody().containsKey("access_token")) {
                return (String) response.getBody().get("access_token");
            }
            throw new RuntimeException("Não foi possível obter o token de admin do client.");
        } catch (HttpClientErrorException e) {
            System.err.println("Erro ao obter token de admin: " + e.getResponseBodyAsString());
            throw new RuntimeException("Não foi possível obter token de admin. Verifique se o client tem 'Service Accounts Enabled' e as roles de admin necessárias.", e);
        }
    }
}