package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.UsuariosOld;
import com.Notifica.service.UsuariosServiceOld;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosControllerOld {

    @Autowired
    private UsuariosServiceOld usuariosService;

    @PostMapping("/criar")
    public ResponseEntity<UsuariosOld> criarUsuarios(@RequestBody UsuariosOld usuario) {
        try {
            UsuariosOld novoUsuario = usuariosService.criarUsuario(usuario.getUsername(), usuario.getPassword(), usuario.getTipoUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario); // Retornando o objeto criado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/obter/{id}")
    public ResponseEntity<UsuariosOld> obterUsuariosPorId(@PathVariable Long id) {
        try {
            UsuariosOld usuario = usuariosService.obterUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario); // Retornando o objeto encontrado
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
