package com.Notifica.controller.login; // Note que corrigi o 'L' maiúsculo para minúsculo no nome do pacote

public record LoginRequest(
    String login,
    String senha
) {}