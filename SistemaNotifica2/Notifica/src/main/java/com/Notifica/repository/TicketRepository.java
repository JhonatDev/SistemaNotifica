package com.Notifica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Notifica.entity.Ticket;

import jakarta.validation.Valid;

import java.io.ObjectInputFilter.Status;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    //findByStatus
    List<Ticket> findByStatus(@Valid Ticket.Status status);

    List<Ticket> findByRaAlunoAndStatus(String raAluno, com.Notifica.entity.Ticket.Status status);

    List<Ticket> findByRaAluno(String raAluno);

    List<Ticket> findAllByOrderByDataCriacaoDesc();

    List<Ticket> findByStatusOrderByDataCriacaoDesc(com.Notifica.entity.Ticket.Status status);
    
}
