package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Ticket;
import com.Notifica.service.TicketService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Objects;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> criarTicket(@RequestBody Ticket ticket) {
        Ticket novoTicket = ticketService.criarTicket(ticket);
        return new ResponseEntity<>(novoTicket, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> listarTickets() {
        List<Ticket> tickets = ticketService.listarTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    @GetMapping("/aluno/{raAluno}")
    public ResponseEntity<List<Ticket>> listarTicketsPorAluno(@PathVariable String raAluno) {
        List<Ticket> tickets = ticketService.listarTicketsPorAluno(raAluno);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obterTicketPorId(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.obterTicketPorId(id);
        return new ResponseEntity<>(ticket.orElseThrow(NoSuchElementException::new), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Ticket> atualizarStatus(@PathVariable Long id, @RequestParam Ticket.Status status) {
        Ticket ticketAtualizado = ticketService.atualizarStatus(id, status);
        return new ResponseEntity<>(ticketAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTicket(@PathVariable Long id) {
        ticketService.deletarTicket(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
