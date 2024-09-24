package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Administrador;
import com.Notifica.service.AdministradorService;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<Administrador> criarAdministrador(@RequestParam String username, @RequestParam String password) {
        Administrador admin = administradorService.criarAdministrador(username, password);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obterAdministradorPorId(@PathVariable Long id) {
        Administrador admin = administradorService.obterAdministradorPorId(id);
        return ResponseEntity.ok(admin);
    }
}
