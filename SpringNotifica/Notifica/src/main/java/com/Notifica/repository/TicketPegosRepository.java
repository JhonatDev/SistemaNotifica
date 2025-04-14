package com.Notifica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Notifica.entity.TicketPegos;

import jakarta.validation.Valid;

import java.io.ObjectInputFilter.Status;
import java.util.List;

@Repository
public interface TicketPegosRepository extends JpaRepository<TicketPegos, Long> {

    //find all
    List<TicketPegos> findAll();

    
}
