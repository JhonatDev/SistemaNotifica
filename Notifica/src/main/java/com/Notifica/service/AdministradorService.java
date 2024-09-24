package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Notifica.entity.Administrador;
import com.Notifica.repository.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public Administrador criarAdministrador(String username, String password) {
        Administrador admin = new Administrador();
        admin.setUsername(username);
        admin.setPassword(password);

        return administradorRepository.save(admin);
    }

    public Administrador obterAdministradorPorId(Long id) {
        return administradorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Administrador não encontrado."));
    }
}
