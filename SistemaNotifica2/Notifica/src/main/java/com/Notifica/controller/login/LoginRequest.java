package com.Notifica.controller.login;

public record LoginRequest(
        String login,
        String senha,
        String tipoDeUsuario
) {
}
