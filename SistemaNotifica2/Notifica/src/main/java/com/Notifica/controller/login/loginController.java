package com.Notifica.controller.login;

import com.Notifica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class loginController {

    @Autowired
    private UsuarioService pessoaService;

    @PostMapping("login")
        public ResponseEntity<String> logar(@RequestBody LoginRequest login) {
            try {
                String jwtToken = pessoaService.logar(login);  // Obtenha o token aqui
                return ResponseEntity.ok(jwtToken);            // Retorne o JWT se login for bem-sucedido
            } catch (AuthenticationException ex) {
                System.out.println(ex.getMessage());
                 return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>("Erro interno", HttpStatus.BAD_REQUEST); // Alterado para mensagem fixa
    }
}

    @PostMapping("novo-usuario/save")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestParam String usuario,@RequestParam String password,@RequestParam Boolean isAdmin){
        pessoaService.saveNewUser(usuario, password,isAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
