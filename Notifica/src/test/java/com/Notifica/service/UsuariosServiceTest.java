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
import org.springframework.boot.test.mock.mockito.MockBean;

import com.Notifica.repository.UsuariosRepository;
import com.Notifica.entity.Usuarios;
import com.Notifica.service.UsuariosService;

public class UsuariosServiceTest {
    @Mock
    private UsuariosRepository usuariosRepository;

    @InjectMocks
    private UsuariosService usuariosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarUsuarioAdminCompleto() {
        //mock do usuario que será criado
        Usuarios usuario = new Usuarios();
        usuario.setUsername("usuarioTeste");
        usuario.setPassword("senhaTeste");
        Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.ADMIN;
        usuario.setTipoUsuario(tipoUsuario);

        // Define o comportamento do mock para o método save
        when(usuariosRepository.save(usuario)).thenReturn(usuario);

        // Chama o método a ser testado
        Usuarios novoUsuario = usuariosService.criarUsuarios(usuario.getUsername(), usuario.getPassword(), tipoUsuario);

        // Verifica se o resultado é o esperado
        assertEquals(usuario, novoUsuario);

        // Verifica se o método save foi chamado,tem que ser chamado uma vez
        verify(usuariosRepository, times(1)).save(usuario);
    }

    @Test
    public void testCriarUsuarioUsuariosCompleto() {
        //mock do usuario que será criado
        Usuarios usuario = new Usuarios();
        usuario.setUsername("usuarioTeste");
        usuario.setPassword("senhaTeste");
        Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.Usuarios;
        usuario.setTipoUsuario(tipoUsuario);

        // Define o comportamento do mock para o método save
        when(usuariosRepository.save(usuario)).thenReturn(usuario);

        // Chama o método a ser testado
        Usuarios novoUsuario = usuariosService.criarUsuarios(usuario.getUsername(), usuario.getPassword(), tipoUsuario);

        // Verifica se o resultado é o esperado
        assertEquals(usuario, novoUsuario);

        // Verifica se o método save foi chamado,tem que ser chamado uma vez
        verify(usuariosRepository, times(1)).save(usuario);
    }

    @Test
    public void testCriarUsuarioSemUsername() {
        //mock do usuario que será criado
        Usuarios usuario = new Usuarios();
        usuario.setUsername("");
        usuario.setPassword("senhaTeste");
        Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.Usuarios;
        usuario.setTipoUsuario(tipoUsuario);

        // define o comportamento do mock para o método save
        when(usuariosRepository.save(usuario)).thenReturn(usuario);

        // Chama o método a ser testado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuariosService.criarUsuarios(usuario.getUsername(), usuario.getPassword(), tipoUsuario);
        });

        // Verifica se a exceção foi lançada
        assertEquals("O username do usuario é obrigatório.", exception.getMessage());

        // Verifica se o método save foi chamado,não deve ser chamado
        verify(usuariosRepository, times(0)).save(usuario);
    }

    @Test
    public void testCriarUsuarioSemPassword() {
        //mock do usuario que será criado
        Usuarios usuario = new Usuarios();
        usuario.setUsername("usuarioTeste");
        usuario.setPassword("");
        Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.Usuarios;
        usuario.setTipoUsuario(tipoUsuario);

        // define o comportamento do mock para o método save
        when(usuariosRepository.save(usuario)).thenReturn(usuario);

        // Chama o método a ser testado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuariosService.criarUsuarios(usuario.getUsername(), usuario.getPassword(), tipoUsuario);
        });

        // Verifica se a exceção foi lançada
        assertEquals("A senha do usuario é obrigatória.", exception.getMessage());

        // Verifica se o método save foi chamado,não deve ser chamado
        verify(usuariosRepository, times(0)).save(usuario);
    }

    @Test
    public void testCriarUsuarioSemTipoUsuario() {
        //mock do usuario que será criado
        Usuarios usuario = new Usuarios();
        usuario.setUsername("usuarioTeste");
        usuario.setPassword("senhaTeste");
        Usuarios.TipoUsuario tipoUsuario = null;
        usuario.setTipoUsuario(tipoUsuario);

        // define o comportamento do mock para o método save
        when(usuariosRepository.save(usuario)).thenReturn(usuario);

        // Chama o método a ser testado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuariosService.criarUsuarios(usuario.getUsername(), usuario.getPassword(), tipoUsuario);
        });

        // Verifica se a exceção foi lançada
        assertEquals("O tipo de usuario é obrigatório.", exception.getMessage());

        // Verifica se o método save foi chamado,não deve ser chamado
        verify(usuariosRepository, times(0)).save(usuario);
    }

    @Test
    public void testObterUsuarioPorId() {
        //mock do usuario que será obtido
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setUsername("usuarioTeste");
        usuario.setPassword("senhaTeste");
        Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.Usuarios;
        usuario.setTipoUsuario(tipoUsuario);

        // Define o comportamento do mock para o método findById
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Chama o método a ser testado
        Usuarios usuarioObtido = usuariosService.obterUsuariosPorId(1L);

        // Verifica se o resultado é o esperado
        assertEquals(usuario, usuarioObtido);

        // Verifica se o método findById foi chamado
        verify(usuariosRepository, times(1)).findById(1L);

        //caso o usuario não exista
        when(usuariosRepository.findById(2L)).thenReturn(Optional.empty());

        // Chama o método a ser testado
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuariosService.obterUsuariosPorId(2L);
        });

        // Verifica se a exceção foi lançada
        assertEquals("Usuario não encontrado.", exception.getMessage());

        // Verifica se o método findById foi chamado
        verify(usuariosRepository, times(1)).findById(2L);
        
    }
}
