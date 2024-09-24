package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Notifica.entity.Ticket;
import com.Notifica.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket criarTicket(Ticket ticket) {
    	if (ticket.getDescricaoProblema() == null || ticket.getDescricaoProblema().isEmpty()) {
            throw new IllegalArgumentException("A descrição do ticket é obrigatória.");
        }
        return ticketRepository.save(ticket);
    }

    public List<Ticket> listarTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> listarTicketsPorAluno(Long alunoId) {
        return ticketRepository.findByAlunoId(alunoId);
    }

    public Optional<Ticket> obterTicketPorId(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket atualizarStatus(Long id, Ticket.Status status) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(status);
            if (status == Ticket.Status.SOLUCIONADO) {
                ticket.setDataSolucao(LocalDateTime.now());
            }
            return ticketRepository.save(ticket);
        } else {
            throw new RuntimeException("Ticket não encontrado.");
        }
    }

    public void deletarTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
