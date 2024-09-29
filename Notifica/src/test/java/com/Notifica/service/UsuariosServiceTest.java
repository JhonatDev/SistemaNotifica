package com.Notifica.service;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Notifica.repository.UsuariosRepository;
import com.Notifica.entity.Usuarios;
import com.Notifica.service.UsuariosService;

public class UsuariosServiceTest {
     @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuariosService usuariosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarUsuariosCompleto() {
    //mock do usuario que será criado
    Usuarios usuario = new Usuarios();
    usuario.setUsername("usuarioTeste");
    usuario.setPassword("senhaTeste");
    Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.ADMIN;
    usuario.setTipoUsuario(tipoUsuario);

    //mock do password encriptado
    String hashedPassword = "hashedPassword";
    when(passwordEncoder.encode(usuario.getPassword())).thenReturn(hashedPassword);

    // Define o comportamento do mock para o método save O "any(Usuarios.class)" permite capturar qualquer instância de Usuarios
    when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuario);

    // Chama o método a ser testado
    Usuarios novoUsuario = usuariosService.criarUsuario(usuario.getUsername(), usuario.getPassword(), usuario.getTipoUsuario());

    // Verifica se o resultado é o esperado
    assertEquals(usuario.getUsername(), novoUsuario.getUsername());
    assertEquals(usuario.getPassword(), novoUsuario.getPassword());
    assertEquals(usuario.getTipoUsuario(), novoUsuario.getTipoUsuario());

    // Verifica se o método save foi chamado,tem que ser chamado uma vez
    verify(usuariosRepository, times(1)).save(any(Usuarios.class));
    }

    @Test
    public void testCriarUsuariossemUsername() {
    //mock do usuario que será criado
    Usuarios usuario = new Usuarios();
    usuario.setUsername("");
    usuario.setPassword("senhaTeste");
    Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.ADMIN;

    //mock do password encriptado
    String hashedPassword = "hashedPassword";
    when(passwordEncoder.encode(usuario.getPassword())).thenReturn(hashedPassword);

    // Define o comportamento do mock para o método save
    when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuario);

    // Chama o método a ser testado
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        usuariosService.criarUsuario(usuario.getUsername(), usuario.getPassword(), tipoUsuario);
    });

    // Verifica se a exceção foi lançada
    assertTrue(exception.getMessage().contains("O nome de usuário não pode ser vazio."));

    // Verifica se o método save foi chamado,não deve ser chamado
    verify(usuariosRepository, times(0)).save(any(Usuarios.class));
    }

    @Test
    public void testCriarUsuariossemPassword() {
    //mock do usuario que será criado
    Usuarios usuario = new Usuarios();
    usuario.setUsername("usuarioTeste");
    usuario.setPassword("");
    Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.ADMIN;

    //mock do password encriptado
    String hashedPassword = "hashedPassword";
    when(passwordEncoder.encode(usuario.getPassword())).thenReturn(hashedPassword);

    // Define o comportamento do mock para o método save
    when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuario);

    // Chama o método a ser testado
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        usuariosService.criarUsuario(usuario.getUsername(), usuario.getPassword(), tipoUsuario);
    });

    // Verifica se a exceção foi lançada
    assertTrue(exception.getMessage().contains("A senha não pode ser vazia."));

    // Verifica se o método save foi chamado,não deve ser chamado
    verify(usuariosRepository, times(0)).save(any(Usuarios.class));
    }

    @Test
    public void testCriarUsuariossemTipoUsuario() {
    //mock do usuario que será criado
    Usuarios usuario = new Usuarios();
    usuario.setUsername("usuarioTeste");
    usuario.setPassword("senhaTeste");
    usuario.setTipoUsuario(null); 

    //mock do password encriptado
    String hashedPassword = "hashedPassword";
    when(passwordEncoder.encode(usuario.getPassword())).thenReturn(hashedPassword);

    // Define o comportamento do mock para o método save
    when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuario);

    // Chama o método a ser testado
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        usuariosService.criarUsuario(usuario.getUsername(), usuario.getPassword(), usuario.getTipoUsuario());
    });

    // Verifica se a exceção foi lançada
    assertTrue(exception.getMessage().contains("O tipo de usuário não pode ser nulo."));

    // Verifica se o método save foi chamado,não deve ser chamado
    verify(usuariosRepository, times(0)).save(any(Usuarios.class));
    }

    @Test
    public void testObterUsuarioPorId() {
    //mock do usuario que será criado
    Usuarios usuario = new Usuarios();
    usuario.setId(1L);
    usuario.setUsername("usuarioTeste");
    usuario.setPassword("senhaTeste");
    Usuarios.TipoUsuario tipoUsuario = Usuarios.TipoUsuario.ADMIN;
    usuario.setTipoUsuario(tipoUsuario);

    // Define o comportamento do mock para o método findById
    when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));

    // Chama o método a ser testado
    Usuarios usuarioRetornado = usuariosService.obterUsuarioPorId(1L);

    // Verifica se o resultado é o esperado
    assertEquals(usuario.getId(), usuarioRetornado.getId());

    // Verifica se o método findById foi chamado,tem que ser chamado uma vez
    verify(usuariosRepository, times(1)).findById(1L);
    }

    @Test
    public void testObterUsuarioPorIdInexistente() {
    // Define o comportamento do mock para o método findById
    when(usuariosRepository.findById(1L)).thenReturn(Optional.empty());

    // Chama o método a ser testado
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        usuariosService.obterUsuarioPorId(1L);
    });

    // Verifica se a exceção foi lançada
    assertTrue(exception.getMessage().contains("Usuário não encontrado."));

    // Verifica se o método findById foi chamado,tem que ser chamado uma vez
    verify(usuariosRepository, times(1)).findById(1L);
    }
        
}
