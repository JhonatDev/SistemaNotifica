package com.Notifica.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricaoProblema;

    private String local;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataSolucao;

    private String caminhoFoto; // Para armazenar o caminho da foto do problema

    private Long alunoId;

    public enum Status {
        ABERTO, EM_ANDAMENTO, SOLUCIONADO, CANCELADO
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.ABERTO;
    }
}
