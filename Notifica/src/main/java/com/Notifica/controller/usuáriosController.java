package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Usuarios;
import com.Notifica.service.UsuariosService;

@RestController
@RequestMapping("/Usuarios")
public class usuáriosController {

    @Autowired
    private UsuariosService UsuariosService;

    @PostMapping
    public ResponseEntity<Usuarios> criarUsuarios(@RequestParam String username, @RequestParam String password, @RequestParam Usuarios.TipoUsuario tipoUsuario) {
        Usuarios Usuarios = UsuariosService.criarUsuarios(username, password, tipoUsuario);
        return ResponseEntity.ok(Usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obterUsuariosPorId(@PathVariable Long id) {
        Usuarios Usuarios = UsuariosService.obterUsuariosPorId(id);
        return ResponseEntity.ok(Usuarios);
    }
}
