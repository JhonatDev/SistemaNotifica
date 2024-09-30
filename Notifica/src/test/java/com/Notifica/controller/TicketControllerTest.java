package com.Notifica.controller;

import com.Notifica.entity.Ticket;
import com.Notifica.service.TicketService;

import Notifica.service.SecurityConfig;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TicketController.class)
@Import(SecurityConfig.class) // Certifique-se de que isso está correto
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    public void testCriarTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescricaoProblema("Test Ticket");

        Mockito.when(ticketService.criarTicket(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"descricaoProblema\": \"Test Ticket\"}")) // Ajuste o campo
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descricaoProblema").value("Test Ticket")); // Ajuste o campo
    }

    @Test
    public void testListarTickets() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescricaoProblema("Test Ticket");

        Mockito.when(ticketService.listarTickets()).thenReturn(List.of(ticket));

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].descricaoProblema").value("Test Ticket")); // Ajuste o campo
    }

    @Test
    public void testListarTicketsPorAluno() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescricaoProblema("Test Ticket");

        Mockito.when(ticketService.listarTicketsPorAluno("123456")).thenReturn(List.of(ticket));

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/aluno/123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].descricaoProblema").value("Test Ticket")); // Ajuste o campo
    }

    @Test
    public void testObterTicketPorId() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescricaoProblema("Test Ticket");

        Mockito.when(ticketService.obterTicketPorId(1L)).thenReturn(java.util.Optional.of(ticket));

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descricaoProblema").value("Test Ticket")); // Ajuste o campo
    }

    @Test
    public void testAtualizarStatus() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescricaoProblema("Test Ticket");

        Mockito.when(ticketService.atualizarStatus(1L, Ticket.Status.CANCELADO)).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.patch("/tickets/1/CANCELADO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descricaoProblema").value("Test Ticket")); // Ajuste o campo
    }

    @Test
    public void testDeletarTicket() throws Exception {
        Mockito.doNothing().when(ticketService).deletarTicket(1L); // Mock do método de deletar

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/1"))
                .andExpect(status().isNoContent());
    }
}
