package com.Notifica.controller.tickts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Ticket;
import com.Notifica.entity.TicketPegos;
import com.Notifica.service.TicketPegosService;

import jakarta.validation.Valid;
import lombok.val;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Objects;

@RestController
@RequestMapping("/ticketsPegos")
@CrossOrigin(origins = "*")
public class TicketPegosController {

    @Autowired
    private TicketPegosService ticketPegosService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('admin', 'funcionario')")
    public ResponseEntity<TicketPegos> save(@Valid @RequestBody TicketPegos ticketPegos) {
        TicketPegos savedTicketPegos = ticketPegosService.save(ticketPegos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicketPegos);
    }
    @DeleteMapping("/removeByNomeUsuarioPego/{nomeUsuarioPego}")
    @PreAuthorize("hasRole('admin', 'funcionario')")
    public ResponseEntity<Map<String, String>> removeByNomeUsuarioPego(@PathVariable String nomeUsuarioPego) {
        ticketPegosService.removeByNomeUsuarioPego(nomeUsuarioPego);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ticket removido com sucesso!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('admin', 'funcionario')")
    public ResponseEntity<List<TicketPegos>> getAll() {
        List<TicketPegos> ticketPegos = ticketPegosService.findAll();
        return ResponseEntity.ok(ticketPegos);
    }
    @GetMapping("/findByIdTicket/{idTicket}")
    @PreAuthorize("hasRole('admin', 'funcionario')")
    public ResponseEntity<List<TicketPegos>> getByIdTicket(@PathVariable Long idTicket) {
        List<TicketPegos> ticketPegos = ticketPegosService.findByIdTicket(idTicket);
        return ResponseEntity.ok(ticketPegos);
    }
}
