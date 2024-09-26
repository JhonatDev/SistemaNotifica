package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Ticket;
import com.Notifica.service.TicketService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> criarTicket(@RequestBody Ticket ticket) {
        Ticket novoTicket = ticketService.criarTicket(ticket);
        return ResponseEntity.ok(novoTicket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> listarTickets() {
        List<Ticket> tickets = ticketService.listarTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/aluno/{raAluno}")
    public ResponseEntity<List<Ticket>> listarTicketsPorAluno(@PathVariable String raAluno) {
        List<Ticket> tickets = ticketService.listarTicketsPorAluno(raAluno);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obterTicketPorId(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.obterTicketPorId(id);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Ticket> atualizarStatus(@PathVariable Long id, @RequestParam Ticket.Status status) {
        Ticket ticketAtualizado = ticketService.atualizarStatus(id, status);
        return ResponseEntity.ok(ticketAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTicket(@PathVariable Long id) {
        ticketService.deletarTicket(id);
        return ResponseEntity.noContent().build();
    }
}
