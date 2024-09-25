package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Notifica.entity.Usuarios;
import com.Notifica.repository.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository UsuariosRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuarios criarUsuarios(String username, String password) {
        Usuarios Usuarios = new Usuarios();
        Usuarios.setUsername(username);
        
        String hashedPassword = passwordEncoder.encode(password);  //hash da senha
        Usuarios.setPassword(hashedPassword);

        return UsuariosRepository.save(Usuarios);
    }

    public Usuarios obterUsuariosPorId(Long id) {
        return UsuariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuarios não encontrado."));
    }
}
