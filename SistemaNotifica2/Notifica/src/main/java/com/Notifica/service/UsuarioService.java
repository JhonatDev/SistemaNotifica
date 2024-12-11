package com.Notifica.service;

import com.Notifica.controller.login.LoginRequest;
import com.Notifica.entity.UsuarioEntity;
import com.Notifica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private JwtUtils jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;


    public String logar(LoginRequest login) {
        // Recupera o usuário a partir do repositório
        var data = repository.findByUsername(login.login());
    
        // Verifica se o usuário existe
        if (data.isEmpty()) {
            throw new AuthenticationException("Usuário não encontrado") {};
        }
    
        UsuarioEntity user = data.get();
    
        // Verifica se a senha fornecida corresponde à senha criptografada
        if (!passwordEncoder.matches(login.senha(), user.getPassword())) {
            throw new AuthenticationException("Credenciais incorretas") {};
        }
    
        // Realiza a autenticação
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.login(),
                        login.senha()
                )
        );
    
        // Gera o token JWT
        String jwtToken = jwtService.generateToken(user);
    
        return jwtToken;
    }


    public void saveNewUser(String usuario, String password, Boolean isAdmin) {
        repository.save(
                new UsuarioEntity(
                        null,
                        usuario,
                        passwordEncoder.encode(password),
                        (isAdmin?"ROLE_admin":"ROLE_user")
                )
        );
    }

    public UsuarioEntity findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }
}
