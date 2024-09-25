package com.Notifica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição do problema é obrigatória")
    @Column(nullable = false)
    private String descricaoProblema;

    private String local;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataSolucao;

    private String caminhoFoto; // Para armazenar o caminho da foto do problema

    @NotBlank(message = "O RA do aluno é obrigatório")
    @Column(nullable = false)
    private String raAluno; // RA do aluno é obrigatório

    public enum Status {
        ABERTO, EM_ANDAMENTO, SOLUCIONADO, CANCELADO
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.ABERTO;
    }
    
    public void atualizarStatus(Status novoStatus) {
        if (novoStatus == Status.SOLUCIONADO) {
            this.dataSolucao = LocalDateTime.now(); // Registra a data de solução ao mudar para SOLUCIONADO
        }
        this.status = novoStatus;
    }
}
