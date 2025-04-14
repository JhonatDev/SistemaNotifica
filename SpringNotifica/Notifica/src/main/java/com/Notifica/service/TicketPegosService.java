package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Notifica.entity.TicketPegos;
import com.Notifica.repository.TicketPegosRepository;

import jakarta.validation.Valid;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketPegosService  {

    @Autowired
    private TicketPegosRepository ticketPegosRepository;

    // Save a TicketPegos
    public TicketPegos save(@Valid TicketPegos ticketPegos) {
        return ticketPegosRepository.save(ticketPegos);
    }

    // Remove a TicketPegos pelo nome do usuario que pegou o ticket
    public void removeByNomeUsuarioPego(String nomeUsuarioPego) {
        List<TicketPegos> ticketPegosList = ticketPegosRepository.findAll();
        for (TicketPegos ticketPegos : ticketPegosList) {
            if (ticketPegos.getNomeUsuarioPego().equals(nomeUsuarioPego)) {
                ticketPegosRepository.delete(ticketPegos);
            }
        }
    }

    // Find all TicketPegos
    public List<TicketPegos> findAll() {
        return ticketPegosRepository.findAll();
    }

    //achar todos os nomes de usuarios que pegaram tickets o messmo pelo id
    public List<TicketPegos> findByIdTicket(Long idTicket) {
        return ticketPegosRepository.findAll().stream()
                .filter(ticketPegos -> ticketPegos.getId().equals(idTicket))
                .toList();
    }



}
