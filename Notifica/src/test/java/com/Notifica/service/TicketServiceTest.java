package com.Notifica.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.Notifica.repository.TicketRepository;
import com.Notifica.entity.Ticket;
import com.Notifica.service.TicketService;

public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarTicketCompleto() {
        //mock do ticket que será criado
        Ticket ticket = new Ticket();
        ticket.setDescricaoProblema("Problema teste");
        ticket.setLocal("Local teste");
        ticket.setRaAluno("123456");
        ticket.setCaminhoFoto("caminho/foto");
        Ticket.Status status = Ticket.Status.ABERTO;
        ticket.setStatus(status);

        // Define o comportamento do mock para o método save
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Chama o método a ser testado
        Ticket novoTicket = ticketService.criarTicket(ticket);

        // Verifica se o resultado é o esperado
        assertEquals(ticket, novoTicket);

        // Verifica se o método save foi chamado,tem que ser chamado uma vez
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void testCriarTicketSemDescricaoProblema() {
        //mock do ticket que será criado
        Ticket ticket = new Ticket();
        ticket.setDescricaoProblema("");
        ticket.setLocal("Local teste");
        ticket.setRaAluno("123456");
        ticket.setCaminhoFoto("caminho/foto");
        Ticket.Status status = Ticket.Status.ABERTO;
        ticket.setStatus(status);

        // define o comportamento do mock para o método save
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Chama o método a ser testado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.criarTicket(ticket);
        });

        // Verifica se a exceção foi lançada
        assertEquals("A descrição do ticket é obrigatória.", exception.getMessage());

        // Verifica se o método save foi chamado,não deve ser chamado
        verify(ticketRepository, times(0)).save(ticket);
    }
    
    @Test
    public void testCriarTicketSemRaAluno() {
        //mock do ticket que será criado
        Ticket ticket = new Ticket();
        ticket.setDescricaoProblema("Problema teste");
        ticket.setLocal("Local teste");
        ticket.setRaAluno("");
        ticket.setCaminhoFoto("caminho/foto");
        Ticket.Status status = Ticket.Status.ABERTO;
        ticket.setStatus(status);

        // define o comportamento do mock para o método save
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Chama o método a ser testado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.criarTicket(ticket);
        });

        // Verifica se a exceção foi lançada
        assertEquals("O RA do aluno é obrigatório.", exception.getMessage());

        // Verifica se o método save foi chamado,não deve ser chamado
        verify(ticketRepository, times(0)).save(ticket);
    }

    @Test
    public void testListarTickets() {
        //mock da lista de tickets
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket(), new Ticket());

        // define o comportamento do mock para o método findAll
        when(ticketRepository.findAll()).thenReturn(tickets);

        // Chama o método a ser testado
        List<Ticket> ticketsRetornados = ticketService.listarTickets();

        // Verifica se o resultado é o esperado
        assertEquals(tickets, ticketsRetornados);

        // Verifica se o método findAll foi chamado,tem que ser chamado uma vez
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void testListarTicketsPorAluno() {
        //mock da lista de tickets
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket(), new Ticket());

        // define o comportamento do mock para o método findByRaAluno
        when(ticketRepository.findByRaAluno("123456")).thenReturn(tickets);

        // Chama o método a ser testado
        List<Ticket> ticketsRetornados = ticketService.listarTicketsPorAluno("123456");

        // Verifica se o resultado é o esperado
        assertEquals(tickets, ticketsRetornados);

        // Verifica se o método findByRaAluno foi chamado,tem que ser chamado uma vez
        verify(ticketRepository, times(1)).findByRaAluno("123456");
    }

    @Test
    public void testObterTicketPorId() {
        //mock do ticket que será retornado
        Ticket ticket = new Ticket();

        // define o comportamento do mock para o método findById
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // Chama o método a ser testado
        Optional<Ticket> ticketRetornado = ticketService.obterTicketPorId(1L);

        // Verifica se o resultado é o esperado
        assertTrue(ticketRetornado.isPresent());
        assertEquals(ticket, ticketRetornado.get());

        // Verifica se o método findById foi chamado,tem que ser chamado uma vez
        verify(ticketRepository, times(1)).findById(1L);

        // Chama o método a ser testado com um id que não existe
        ticketRetornado = ticketService.obterTicketPorId(2L);

        // Verifica se o resultado é o esperado
        assertTrue(ticketRetornado.isEmpty());
    }

    @Test
    public void testAtualizarStatus() {
        //mock do ticket que será atualizado
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescricaoProblema("Problema teste");
        ticket.setLocal("Local teste");
        ticket.setRaAluno("123456");
        ticket.setCaminhoFoto("caminho/foto");
        Ticket.Status status = Ticket.Status.ABERTO;
        ticket.setStatus(status);

        // define o comportamento do mock para o método findById
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // define o comportamento do mock para o método save
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Chama o método a ser testado
        Ticket ticketAtualizado = ticketService.atualizarStatus(1L, Ticket.Status.EM_ANDAMENTO);

        // Verifica se o resultado é o esperado
        assertEquals(Ticket.Status.EM_ANDAMENTO, ticketAtualizado.getStatus());

        // Verifica se o método findById foi chamado,tem que ser chamado uma vez
        verify(ticketRepository, times(1)).findById(1L);

        // Verifica se o método save foi chamado,tem que ser chamado uma vez
        verify(ticketRepository, times(1)).save(ticket);
    }


}
