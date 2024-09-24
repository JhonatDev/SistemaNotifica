package com.Notifica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Notifica.entity.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
}
