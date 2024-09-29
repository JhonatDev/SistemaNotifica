package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Notifica.entity.Usuarios;
import com.Notifica.repository.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;
    
    @Autowired  // Injeta o BCryptPasswordEncoder mock
    private BCryptPasswordEncoder passwordEncoder;

    public Usuarios criarUsuario(String username, String password, Usuarios.TipoUsuario tipoUsuario) {
        Usuarios usuario = new Usuarios();
        usuario.setUsername(username);
        usuario.setTipoUsuario(tipoUsuario);

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("O nome de usuário não pode ser vazio.");
        } else if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        } else if (tipoUsuario == null) {
            throw new IllegalArgumentException("O tipo de usuário não pode ser nulo.");
        }

        // Usa o mock do BCryptPasswordEncoder
        String hashedPassword = passwordEncoder.encode(password);
        usuario.setPassword(hashedPassword);

        return usuariosRepository.save(usuario);
    }

    public Usuarios obterUsuarioPorId(Long id) {
        return usuariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
}
