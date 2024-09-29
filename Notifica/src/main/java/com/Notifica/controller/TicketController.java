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

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/criarTickets")
    public ResponseEntity<?> criarTicket(@RequestBody Ticket ticket) {
        try {
            Ticket novoTicket = ticketService.criarTicket(ticket);
            String mensagemSucesso = "Ticket criado com sucesso!";
            return ResponseEntity.ok(mensagemSucesso + " ID: " + novoTicket.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o ticket.");
        }
    }

    @GetMapping("/listarTickets")
    public ResponseEntity<?> listarTickets() {
        try {
            List<Ticket> tickets = ticketService.listarTickets();
            String mensagemSucesso = "Tickets listados com sucesso!";
            return ResponseEntity.ok(mensagemSucesso + " Total de tickets: " + tickets.size());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar os tickets.");
        }
    }


    @GetMapping("/aluno/{raAluno}")
    public ResponseEntity<?> listarTicketsPorAluno(@PathVariable String raAluno) {
        try {
            List<Ticket> tickets = ticketService.listarTicketsPorAluno(raAluno);
            if (tickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum ticket encontrado para o aluno com RA: " + raAluno);
            }
            String mensagemSucesso = "Tickets listados com sucesso para o aluno com RA: " + raAluno;
            return ResponseEntity.ok(mensagemSucesso + ". Total de tickets: " + tickets.size());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar tickets para o aluno com RA: " + raAluno);
        }
    }

    @GetMapping("/buscarId/{id}")
    public ResponseEntity<?> obterTicketPorId(@PathVariable Long id) {
        try {
            Optional<Ticket> ticket = ticketService.obterTicketPorId(id);
            if (ticket.isPresent()) {
                return ResponseEntity.ok("Ticket encontrado com sucesso! Detalhes: " + ticket.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket não encontrado para o ID: " + id);
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter o ticket com ID: " + id);
        }
    }


    @PatchMapping("/{id}/atualizarStatus")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam Ticket.Status status) {
        try {
            Ticket ticketAtualizado = ticketService.atualizarStatus(id, status);
            return ResponseEntity.ok("Status do ticket atualizado com sucesso! Detalhes: " + ticketAtualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket não encontrado para o ID: " + id);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status inválido: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o status do ticket com ID: " + id);
        }
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarTicket(@PathVariable Long id) {
        try {
            ticketService.deletarTicket(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket não encontrado para o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o ticket com ID: " + id);
        }
    }

}
