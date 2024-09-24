package com.Notifica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.Funcionario;
import com.Notifica.service.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestParam String username, @RequestParam String password) {
        Funcionario funcionario = funcionarioService.criarFuncionario(username, password);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> obterFuncionarioPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.obterFuncionarioPorId(id);
        return ResponseEntity.ok(funcionario);
    }
}
