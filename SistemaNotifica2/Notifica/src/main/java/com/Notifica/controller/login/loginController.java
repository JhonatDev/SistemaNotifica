package com.Notifica.controller.login;

import com.Notifica.entity.Usuarios;
import com.Notifica.service.UsuariosService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class loginController {

    @Autowired
    private UsuariosService usuariosService;

    //login
    @PostMapping
    public ResponseEntity<Usuarios> loginuUsuarios(@Valid @RequestBody Usuarios usuario) {
    
        System.out.println("Tentando login com username: " + usuario.getUsername());
        System.out.println("Tentando login com password: " + usuario.getPassword());
        Usuarios usuarioLogado = usuariosService.loginuUsuarios(usuario.getUsername(), usuario.getPassword());
        
        return new ResponseEntity<>(usuarioLogado, HttpStatus.OK);
        
   }

   /*postaman
    * {
    "username": "admin",
    "password": "admin"
    }
    
    */

        
}
