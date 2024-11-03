package com.Notifica.controller;

import com.Notifica.entity.Usuarios;
import com.Notifica.service.UsuariosService;

import com.Notifica.controller.UsuariosController;
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

@WebMvcTest(UsuariosController.class)
@Import(SecurityConfig.class) // Certifique-se de que isso está correto
public class UsuariosControllerTest {
   
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuariosService usuariosService;

    @Test
    public void testCriarUsuarios() throws Exception {
        Usuarios usuarios = new Usuarios();
        usuarios.setId(1L);
        usuarios.setUsername("Test Usuarios");
        usuarios.setPassword("Test Usuarios");
        usuarios.setTipoUsuario(Usuarios.TipoUsuario.ADMIN);

        Mockito.when(usuariosService.criarUsuario(any(String.class), any(String.class), any(Usuarios.TipoUsuario.class))).thenReturn(usuarios);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Test Usuarios\", \"password\": \"Test Usuarios\", \"tipoUsuario\": \"ADMIN\"}")) // Ajuste o campo
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Test Usuarios")) // Ajuste o campo
                .andExpect(jsonPath("$.password").value("Test Usuarios")) // Ajuste o campo
                .andExpect(jsonPath("$.tipoUsuario").value("ADMIN")); // Ajuste o campo

    }

    @Test
    public void testCriarUsuariosInternalServerError() throws Exception {
        Mockito.when(usuariosService.criarUsuario(any(String.class), any(String.class), any(Usuarios.TipoUsuario.class))).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Test Usuarios\", \"password\": \"Test Usuarios\", \"tipoUsuario\": \"ADMIN\"}")) // Ajuste o campo
                .andExpect(status().isInternalServerError());
    }
        
    @Test
    public void testObterUsuariosPorId() throws Exception {
        Usuarios usuarios = new Usuarios();
        usuarios.setId(1L);
        usuarios.setUsername("Test Usuarios");
        usuarios.setPassword("Test Usuarios");
        usuarios.setTipoUsuario(Usuarios.TipoUsuario.ADMIN);

        Mockito.when(usuariosService.obterUsuarioPorId(any(Long.class))).thenReturn(usuarios);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/obter/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Test Usuarios")) // Ajuste o campo
                .andExpect(jsonPath("$.password").value("Test Usuarios")) // Ajuste o campo
                .andExpect(jsonPath("$.tipoUsuario").value("ADMIN")); // Ajuste o campo
    }

    @Test
    public void testObterUsuariosPorIdNotFound() throws Exception {
        Mockito.when(usuariosService.obterUsuarioPorId(any(Long.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/obter/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testObterUsuariosPorIdInternalServerError() throws Exception {
        Mockito.when(usuariosService.obterUsuarioPorId(any(Long.class))).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/obter/1"))
                .andExpect(status().isInternalServerError());
    }



}

