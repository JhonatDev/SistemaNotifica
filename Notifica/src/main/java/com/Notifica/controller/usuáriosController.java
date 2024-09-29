package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.Notifica.entity.Usuarios;
import com.Notifica.service.UsuariosService;

@RestController
@RequestMapping("/Usuarios")
public class usuáriosController {

    @Autowired
    private UsuariosService UsuariosService;

    @PostMapping
    public ResponseEntity<Usuarios> criarUsuarios(@RequestBody Usuarios usuarios) {
        Usuarios usuarioCriado = UsuariosService.criarUsuario(usuarios.getUsername(), usuarios.getPassword(), usuarios.getTipoUsuario());
        return new ResponseEntity<>(usuarioCriado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obterUsuarioPorId(@PathVariable Long id) {
        Usuarios Usuarios = UsuariosService.obterUsuarioPorId(id);
        return new ResponseEntity<>(Usuarios, HttpStatus.OK);
    }
}
