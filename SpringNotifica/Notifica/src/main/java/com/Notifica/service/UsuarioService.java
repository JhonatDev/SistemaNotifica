package com.Notifica.service;

import com.Notifica.controller.login.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;       // ex: http://localhost:8081/realms/Notifica

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Mantém assinatura original: recebe LoginRequest e devolve o JWT (access_token).
     * Agora faz POST direto no token endpoint do Keycloak.
     */
    public String logar(LoginRequest login) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Monta o form-urlencoded
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", login.login());
        form.add("password", login.senha());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        String tokenUrl = issuerUri + "/protocol/openid-connect/token";

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        if (!response.getStatusCode().is2xxSuccessful()
                || response.getBody() == null
                || !response.getBody().containsKey("access_token")) {
            throw new RuntimeException("Falha ao autenticar no Keycloak");
        }

        // Retorna só o access_token, como antes
        return response.getBody().get("access_token").toString();
    }

    /**
     * Mantém assinatura original: recebe usuário, senha e flag isAdmin.
     * Agora usa Admin API do Keycloak para criar o usuário.
     */
    public void saveNewUser(String usuario, String password, Boolean isAdmin) {
        // 1) Pega token admin via client_credentials
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> authForm = new LinkedMultiValueMap<>();
        authForm.add("grant_type", "client_credentials");
        authForm.add("client_id", clientId);
        authForm.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> authReq = new HttpEntity<>(authForm, authHeaders);
        String tokenUrl = issuerUri + "/protocol/openid-connect/token";
        ResponseEntity<Map> authResp = restTemplate.postForEntity(tokenUrl, authReq, Map.class);
        if (!authResp.getStatusCode().is2xxSuccessful()
                || authResp.getBody() == null
                || !authResp.getBody().containsKey("access_token")) {
            throw new RuntimeException("Falha ao obter token admin do Keycloak");
        }
        String adminToken = authResp.getBody().get("access_token").toString();

        // 2) Chama endpoint de criação de users do Keycloak
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("username", usuario);
        payload.put("enabled", true);
        payload.put("credentials", List.of(
            Map.of(
                "type", "password",
                "value", password,
                "temporary", false
            )
        ));
        // Atribui realm role 'admin' ou 'user'
        payload.put("realmRoles", isAdmin ? List.of("admin") : List.of("user"));

        HttpEntity<Map<String, Object>> req = new HttpEntity<>(payload, headers);
        String usersUrl = issuerUri + "/admin/realms/Notifica/users";

        ResponseEntity<Void> usersResp = restTemplate.postForEntity(usersUrl, req, Void.class);
        if (!usersResp.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Falha ao criar usuário no Keycloak");
        }
    }

    // Se tiver outros métodos (ex: findByUsername) e ainda precisar deles,
    // você pode mantê-los aqui abaixo.
}
