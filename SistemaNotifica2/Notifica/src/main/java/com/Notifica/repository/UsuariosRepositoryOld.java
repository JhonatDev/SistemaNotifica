package com.Notifica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Notifica.entity.UsuariosOld;

@Repository
public interface UsuariosRepositoryOld extends JpaRepository<UsuariosOld, Long> {

    UsuariosOld findByUsername(String username);

    UsuariosOld findByUsernameAndPassword(String username, String password);
}
