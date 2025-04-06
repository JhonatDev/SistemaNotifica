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

    @NotBlank(message = "O resumo do problema é obrigatório")
    @Column(nullable = false)
    private String resumoProblema;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Local local;

    private String outroLocal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProblema tipoProblema;

    private String outrovtipoProblema;

    @NotBlank(message = "O subtipo do problema é obrigatório")
    @Column(nullable = false)
    private String subtipoProblema;

    private String outroSubtipoProblema;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ABERTO;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataSolucao;

    private String caminhoFoto; // Para armazenar o caminho da foto do problema

    @NotBlank(message = "O RA do aluno é obrigatório")
    @Column(nullable = false)
    private String raAluno;

    private String funcionarioResponsavel;

    // Enumerações para Status, Local e TipoProblema
    public enum Status {
        ABERTO, EM_ANDAMENTO, SOLUCIONADO, CANCELADO
    }

    public enum Local {
        OUTRO, BIBLIOTECA, SALA, LABORATORIO
    }

    public enum TipoProblema {
        OUTRO, ELETRICO, INFRAESTRUTURA, MOBILIARIO, REDE, TECNOLOGIA
    }
    
}
