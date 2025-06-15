package com.Notifica.controller.tickts;

import com.Notifica.entity.Ticket;
import com.Notifica.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*") // Em produção, é recomendado especificar as origens ex: "http://localhost:4200"
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * Cria um novo ticket.
     * Acessível por admin, funcionário e aluno.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @PostMapping("/criar")
    public ResponseEntity<Ticket> criarTicket(@Valid @RequestBody Ticket ticket) {
        Ticket ticketCriado = ticketService.criarTicket(ticket);
        return new ResponseEntity<>(ticketCriado, HttpStatus.CREATED);
    }

    /**
     * Lista todos os tickets em ordem decrescente de data de criação.
     * Acessível por admin, funcionário e aluno.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @GetMapping("/listar")
    public ResponseEntity<List<Ticket>> listarTickets() {
        List<Ticket> tickets = ticketService.listarTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Busca um ticket específico pelo seu ID.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @GetMapping("/buscarID/{id}")
    public ResponseEntity<Ticket> buscarTicket(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.buscarTicket(id);
        
        return ticket.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Atualiza um ticket existente.
     * Acessível por admin, funcionário e aluno.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Ticket> atualizarTicket(@PathVariable Long id, @Valid @RequestBody Ticket ticket) {
        Ticket ticketAtualizado = ticketService.atualizarTicket(id, ticket);
        return new ResponseEntity<>(ticketAtualizado, HttpStatus.OK);
    }

    /**
     * Deleta um ticket.
     * Acessível por admin e funcionário.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Map<String, String>> deletarTicket(@PathVariable Long id) {
        ticketService.deletarTicket(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ticket deletado com sucesso");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Marca um ticket como "EM_ANDAMENTO".
     * Acessível por admin e funcionário.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario')")
    @PutMapping("/iniciar/{id}/{funcionarioResponsavel}")
    public ResponseEntity<Ticket> iniciarTicket(@PathVariable Long id, @PathVariable String funcionarioResponsavel) {
        Ticket ticketIniciado = ticketService.iniciarTicket(id, funcionarioResponsavel);
        return new ResponseEntity<>(ticketIniciado, HttpStatus.OK);
    }

    /**
     * Reverte um ticket para o status "ABERTO".
     * Acessível por admin e funcionário.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario')")
    @PutMapping("/voltarAberto/{id}")
    public ResponseEntity<Ticket> voltarAberto(@PathVariable Long id) {
        Ticket ticketAberto = ticketService.voltarTicketParaAberto(id);
        return new ResponseEntity<>(ticketAberto, HttpStatus.OK);
    }

    /**
     * Marca um ticket como "SOLUCIONADO".
     * Acessível por admin e funcionário.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario')")
    @PutMapping("/solucionar/{id}")
    public ResponseEntity<Ticket> solucionarTicket(@PathVariable Long id) {
        Ticket ticketSolucionado = ticketService.solucionarTicket(id);
        return new ResponseEntity<>(ticketSolucionado, HttpStatus.OK);
    }

    /**
     * Marca um ticket como "CANCELADO".
     * Acessível por admin, funcionário e aluno.
     */
    @PreAuthorize("hasAnyRole('aluno', 'admin', 'funcionario')")
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Ticket> cancelarTicket(@PathVariable Long id) {
        Ticket ticketCancelado = ticketService.cancelarTicket(id);
        return new ResponseEntity<>(ticketCancelado, HttpStatus.OK);
    }

    /**
     * Lista todos os tickets que correspondem a um status específico.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @GetMapping("/listarPorStatus/{status}")
    public ResponseEntity<List<Ticket>> listarTicketsPorStatus(@PathVariable Ticket.Status status) {
        List<Ticket> ticketsPorStatus = ticketService.listarTicketsPorStatus(status);
        return new ResponseEntity<>(ticketsPorStatus, HttpStatus.OK);
    }

    /**
     * Busca tickets por RA e Status.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @GetMapping("/buscarPorRaEStatus/{raAluno}/{status}")
    public ResponseEntity<List<Ticket>> buscarTicketsPorRaEStatus(@PathVariable String raAluno, @PathVariable Ticket.Status status) {
        List<Ticket> ticketsPorRaEStatus = ticketService.buscarTicketsPorRaEStatus(raAluno, status);
        return new ResponseEntity<>(ticketsPorRaEStatus, HttpStatus.OK);
    }

    /**
     * Busca todos os tickets de um aluno específico pelo RA.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @GetMapping("/buscarPorRa/{raAluno}")
    public ResponseEntity<List<Ticket>> buscarTicketsPorRa(@PathVariable String raAluno) {
        List<Ticket> ticketsPorRa = ticketService.buscarTicketsPorRa(raAluno);
        return new ResponseEntity<>(ticketsPorRa, HttpStatus.OK);
    }

    /**
     * Busca todos os tickets de um aluno, exceto os cancelados.
     */
    @PreAuthorize("hasAnyRole('admin', 'funcionario', 'aluno')")
    @GetMapping("/buscarPorRaSemCancelados/{raAluno}")
    public ResponseEntity<List<Ticket>> buscarTicketsPorRaSemCancelados(@PathVariable String raAluno) {
        List<Ticket> ticketsPorRaSemCancelados = ticketService.buscarTicketsPorRaSemCancelados(raAluno);
        return new ResponseEntity<>(ticketsPorRaSemCancelados, HttpStatus.OK);
    }
}