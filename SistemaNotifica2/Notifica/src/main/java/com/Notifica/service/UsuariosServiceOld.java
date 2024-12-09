package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Notifica.entity.UsuariosOld;
import com.Notifica.repository.UsuariosRepositoryOld;

@Service
public class UsuariosServiceOld {

    @Autowired
    private UsuariosRepositoryOld usuariosRepository;
    
    @Autowired  // Injeta o BCryptPasswordEncoder mock
    private BCryptPasswordEncoder passwordEncoder;

    public UsuariosOld criarUsuario(String username, String password, UsuariosOld.TipoUsuario tipoUsuario) {
        UsuariosOld usuario = new UsuariosOld();
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

    public UsuariosOld obterUsuarioPorId(Long id) {
        return usuariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public UsuariosOld loginuUsuarios(String username, String password) {
        UsuariosOld usuario = usuariosRepository.findByUsername(username);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser encontrado.");
        } else if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        } else if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new IllegalArgumentException("Usuario ou senha inválidos.");
        } else {
            return usuario;
        }
    }
}
