package com.Notifica.repository;

import com.Notifica.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // --- MÉTODOS EXISTENTES ---
    List<Ticket> findByStatus(Ticket.Status status);
    List<Ticket> findByRaAluno(String raAluno);

    // Necessário para o TicketService
    List<Ticket> findAllByOrderByCreatedDateDesc();

    // Adiciona a ordenação por data para a busca por status
    List<Ticket> findByStatusOrderByCreatedDateDesc(Ticket.Status status);
    
    // Adiciona a ordenação por data para a busca por RA e Status
    List<Ticket> findByRaAlunoAndStatusOrderByCreatedDateDesc(String raAluno, Ticket.Status status);

    // Adiciona a ordenação por data para a busca por RA (necessário para o filtro de 'sem cancelados')
    List<Ticket> findByRaAlunoOrderByCreatedDateDesc(String raAluno);
}