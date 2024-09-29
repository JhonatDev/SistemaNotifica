package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Usuarios;
import com.Notifica.service.UsuariosService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuarios(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Usuarios.TipoUsuario tipoUsuario) {
        try {
            Usuarios usuario = usuariosService.criarUsuario(username, password, tipoUsuario);
            return ResponseEntity.ok("Usuário criado com sucesso! Detalhes: " + usuario);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o usuário: " + e.getMessage());
        }
    }

    @GetMapping("/obter/{id}")
    public ResponseEntity<?> obterUsuariosPorId(@PathVariable Long id) {
        try {
            Usuarios usuario = usuariosService.obterUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok("Usuário encontrado com sucesso! Detalhes: " + usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter o usuário com ID: " + id);
        }
    }
}
