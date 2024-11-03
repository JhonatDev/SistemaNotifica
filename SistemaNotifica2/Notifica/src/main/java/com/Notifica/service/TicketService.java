package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Notifica.entity.Ticket;
import com.Notifica.repository.TicketRepository;

import jakarta.validation.Valid;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // Método para validar campos obrigatórios
    private void validarTicket(Ticket ticket) {
        if (ticket.getResumoProblema() == null || ticket.getResumoProblema().isEmpty()) {
            throw new IllegalArgumentException("Resumo do problema é obrigatório");
        } else if (ticket.getLocal() == null) {
            throw new IllegalArgumentException("Local do problema é obrigatório");
        } else if (ticket.getRaAluno() == null || ticket.getRaAluno().isEmpty()) {
            throw new IllegalArgumentException("RA do aluno é obrigatório");
        } else if (ticket.getTipoProblema() == null) {
            throw new IllegalArgumentException("Tipo do problema é obrigatório");
        } else if (ticket.getSubtipoProblema() == null || ticket.getSubtipoProblema().isEmpty()) {
            throw new IllegalArgumentException("Subtipo do problema é obrigatório");
        } else if (ticket.getTipoProblema().equals(Ticket.TipoProblema.OUTRO) && (ticket.getOutrovtipoProblema() == null || ticket.getOutrovtipoProblema().isEmpty())) {
            throw new IllegalArgumentException("Outro tipo do problema esta em branco é obrigatório");
        } else if (ticket.getSubtipoProblema().equals("OUTRO") && (ticket.getOutroSubtipoProblema() == null || ticket.getOutroSubtipoProblema().isEmpty())) {
            throw new IllegalArgumentException("Outro subtipo do problema esta em branco é obrigatório");
        } else if (!ticket.getTipoProblema().equals(Ticket.TipoProblema.OUTRO) && ticket.getOutrovtipoProblema() != null && !ticket.getOutrovtipoProblema().isEmpty()) {
            throw new IllegalArgumentException("Outro tipo do problema não pode ser preenchido pos o tipo do problema não é outro");
        } else if (!ticket.getSubtipoProblema().equals("OUTRO") && ticket.getOutroSubtipoProblema() != null && !ticket.getOutroSubtipoProblema().isEmpty()) {
            throw new IllegalArgumentException("Outro subtipo do problema não pode ser preenchido pos o subtipo do problema não é outro");
        }

    }

    // Métodos para criar, listar, buscar, atualizar e deletar tickets
    public Ticket criarTicket(Ticket ticket) {
        validarTicket(ticket);
        return ticketRepository.save(ticket);
    }

    // Método para listar todos os tickets
    public List<Ticket> listarTickets() {
        return ticketRepository.findAll();
    }
    

    // Método para buscar um ticket por ID
    public Optional<Ticket> buscarTicket(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do ticket é obrigatório");
        } else if (id <= 0) {
            throw new IllegalArgumentException("ID do ticket deve ser maior que zero");
        } else if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket não encontrado");
        }

        return ticketRepository.findById(id);
    }

    // Método para atualizar um ticket
    public Ticket atualizarTicket(Long id, Ticket ticket) {
        Ticket ticketSalvo = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));
        ticketSalvo.setResumoProblema(ticket.getResumoProblema());
        ticketSalvo.setLocal(ticket.getLocal());
        ticketSalvo.setTipoProblema(ticket.getTipoProblema());
        ticketSalvo.setOutrovtipoProblema(ticket.getOutrovtipoProblema());
        ticketSalvo.setSubtipoProblema(ticket.getSubtipoProblema());
        ticketSalvo.setOutroSubtipoProblema(ticket.getOutroSubtipoProblema());
        ticketSalvo.setRaAluno(ticket.getRaAluno());
        validarTicket(ticketSalvo);

        return ticketRepository.save(ticketSalvo);
    }

    // Método para deletar um ticket
    public void deletarTicket(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do ticket é obrigatório");
        } else if (id <= 0) {
            throw new IllegalArgumentException("ID do ticket deve ser maior que zero");
        } else if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket não encontrado");
        }
        ticketRepository.deleteById(id);
    }

    // Métodos para iniciar, solucionar e cancelar tickets  
    public Ticket iniciarTicket(Long id, String funcionarioResponsavel) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));

        if (ticket.getStatus() == Ticket.Status.EM_ANDAMENTO) {
            throw new IllegalArgumentException("Ticket já está em andamento");
        } else if (ticket.getStatus() == Ticket.Status.SOLUCIONADO) {
            throw new IllegalArgumentException("Ticket já foi solucionado");
        } else if (ticket.getStatus() == Ticket.Status.CANCELADO) {
            throw new IllegalArgumentException("Ticket já foi cancelado");
        } else if (funcionarioResponsavel == null || funcionarioResponsavel.isEmpty()) {
            throw new IllegalArgumentException("Funcionário responsável é obrigatório");
        }
        ticket.setStatus(Ticket.Status.EM_ANDAMENTO);
        ticket.setFuncionarioResponsavel(funcionarioResponsavel);
        return ticketRepository.save(ticket);
    }

    //função para voltar um ticket para aberto
    public Ticket voltarTicketParaAberto(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));
        if (ticket.getStatus() == Ticket.Status.ABERTO) {
            throw new IllegalArgumentException("Ticket já está aberto");
        }
        ticket.setStatus(Ticket.Status.ABERTO);
        ticket.setFuncionarioResponsavel(null);
        return ticketRepository.save(ticket);
    }

    // Método para solucionar um ticket
    public Ticket solucionarTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));
        if (ticket.getStatus() == Ticket.Status.ABERTO) {
            throw new IllegalArgumentException("Ticket ainda não foi iniciado");
        } else if (ticket.getStatus() == Ticket.Status.SOLUCIONADO) {
            throw new IllegalArgumentException("Ticket já foi solucionado");
        } else if (ticket.getStatus() == Ticket.Status.CANCELADO) {
            throw new IllegalArgumentException("Ticket já foi cancelado");
        }
        ticket.setStatus(Ticket.Status.SOLUCIONADO);
        ticket.setDataSolucao(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    // Método para cancelar um ticket
    public Ticket cancelarTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));
        if (ticket.getStatus() == Ticket.Status.CANCELADO) {
            throw new IllegalArgumentException("Ticket já foi cancelado");
        }
        ticket.setStatus(Ticket.Status.CANCELADO);
        return ticketRepository.save(ticket);
    }

    // Método para listar tickets por status
    public List<Ticket> listarTicketsPorStatus(@Valid Ticket.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status do ticket é obrigatório");
        }

        return ticketRepository.findByStatus(status);
    }

}
