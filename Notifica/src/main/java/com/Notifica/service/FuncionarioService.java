package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Notifica.entity.Funcionario;
import com.Notifica.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Funcionario criarFuncionario(String username, String password) {
        Funcionario funcionario = new Funcionario();
        funcionario.setUsername(username);
        
        String hashedPassword = passwordEncoder.encode(password);  //hash da senha
        funcionario.setPassword(hashedPassword);

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario obterFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));
    }
}
