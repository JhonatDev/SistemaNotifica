package com.Notifica.controller.tickts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Ticket;
import com.Notifica.service.TicketService;

import jakarta.validation.Valid;
import lombok.val;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Objects;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Método para criar um ticket
    @PostMapping("/criar")
    public ResponseEntity<Ticket> criarTicket(@Valid @RequestBody Ticket ticket) {
        Ticket ticketCriado = ticketService.criarTicket(ticket);
        return new ResponseEntity<>(ticketCriado, HttpStatus.CREATED);
    }

    // Método para listar todos os tickets
    @GetMapping("/listar")
    public ResponseEntity<List<Ticket>> listarTickets() {
        List<Ticket> tickets = ticketService.listarTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    // Método para buscar um ticket
    @GetMapping("/buscarID/{id}")
    public ResponseEntity<Ticket> buscarTicket(@PathVariable Long id) {
        try {
            Optional<Ticket> ticket = ticketService.buscarTicket(id);
            return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para atualizar um ticket
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Ticket> atualizarTicket(@Valid @PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket ticketAtualizado = ticketService.atualizarTicket(id, ticket);
        return new ResponseEntity<>(ticketAtualizado, HttpStatus.OK);
        
    }

    // Método para deletar um ticket
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Map<String, String>> deletarTicket(@Valid @PathVariable Long id) {
        ticketService.deletarTicket(id);
    
        // Criar um objeto JSON como resposta
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ticket deletado com sucesso");

        // Retornar a resposta como JSON
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Métodos para iniciar, solucionar e cancelar um ticket
    @PutMapping("/iniciar/{funcionarioResponsavel}/{id}")
    public ResponseEntity<Ticket> iniciarTicket(@Valid @PathVariable Long id, @PathVariable String funcionarioResponsavel) { 
        Ticket ticketIniciado = ticketService.iniciarTicket(id, funcionarioResponsavel);
         return new ResponseEntity<>(ticketIniciado, HttpStatus.OK);
        
    }

    // Método para voltar um ticket para aberto
    @PutMapping("/voltarAberto/{id}")
    public ResponseEntity<Ticket> voltarAberto(@PathVariable Long id) {
        Ticket ticketAberto = ticketService.voltarTicketParaAberto(id);
        return new ResponseEntity<>(ticketAberto, HttpStatus.OK);
        
    }

    // Método para solucionar um ticket
    @PutMapping("/solucionar/{id}")
    public ResponseEntity<Ticket> solucionarTicket(@Valid @PathVariable Long id) {
        Ticket ticketSolucionado = ticketService.solucionarTicket(id);
        return new ResponseEntity<>(ticketSolucionado, HttpStatus.OK);
    }

    // Método para cancelar um ticket
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Ticket> cancelarTicket(@Valid @PathVariable Long id) {
        Ticket ticketCancelado = ticketService.cancelarTicket(id);
        return new ResponseEntity<>(ticketCancelado, HttpStatus.OK);
    }

    // Método para listar tickets por status
    @GetMapping("/listarPorStatus/{status}")
    public ResponseEntity<List<Ticket>> listarTicketsPorStatus(@Valid @PathVariable Ticket.Status status) {
        List<Ticket> ticketsPorStatus = ticketService.listarTicketsPorStatus(status);
        return new ResponseEntity<>(ticketsPorStatus, HttpStatus.OK);
    }

    // Método para buscar tickets por RA e status
    @GetMapping("/buscarPorRaEStatus/{raAluno}/{status}")
    public ResponseEntity<List<Ticket>> buscarTicketsPorRaEStatus(@Valid @PathVariable String raAluno, @Valid @PathVariable Ticket.Status status) {
        List<Ticket> ticketsPorRaEStatus = ticketService.buscarTicketsPorRaEStatus(raAluno, status);
        return new ResponseEntity<>(ticketsPorRaEStatus, HttpStatus.OK);
    }

    // Método para buscar tickets por RA
    @GetMapping("/buscarPorRa/{raAluno}")
    public ResponseEntity<List<Ticket>> buscarTicketsPorRa(@Valid @PathVariable String raAluno) {
        List<Ticket> ticketsPorRa = ticketService.buscarTicketsPorRa(raAluno);
        return new ResponseEntity<>(ticketsPorRa, HttpStatus.OK);
    }

}
