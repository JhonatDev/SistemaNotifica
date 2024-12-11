package com.Notifica.config;

import com.Notifica.entity.UsuarioEntity;
import com.Notifica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityManager {

    @Autowired
    private UsuarioRepository loginRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Codificador de senhas
    }

    @Bean
    public CommandLineRunner initializeData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o usuário admin padrão já existe
            loginRepository.findByUsername("admin").ifPresentOrElse(
                user -> {},
                () -> {
                    // Se não existir, cria o usuário padrão admin
                    UsuarioEntity admin = new UsuarioEntity();
                    admin.setUsername("admin");
                    admin.setPassword(passwordEncoder.encode("admin"));  // Codifica a senha
                    admin.setRole("admin");  // Atribui o papel de administrador
                    usuarioRepository.save(admin);  // Salva o usuário no banco de dados
                }
            );
            // Verifica se o usuário padrão já existe
            loginRepository.findByUsername("user").ifPresentOrElse(
                user -> {},
                () -> {
                    // Se não existir, cria o usuário padrão user
                    UsuarioEntity user = new UsuarioEntity();
                    user.setUsername("user");
                    user.setPassword(passwordEncoder.encode("user"));  // Codifica a senha
                    user.setRole("user");  // Atribui o papel de usuário
                    usuarioRepository.save(user);  // Salva o usuário no banco de dados
                }
            );
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> loginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
