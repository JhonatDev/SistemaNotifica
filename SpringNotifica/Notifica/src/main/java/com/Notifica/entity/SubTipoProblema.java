package com.Notifica.entity;

// import com.Notifica.entity.Ticket.TipoProblema; // O enum está duplicado, pode usar o local
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class SubTipoProblema extends Auditable { // 1. Herda de Auditable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O SubtipoProblema é obrigatório")
    @Column(nullable = false)
    private String subtipoProblema; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProblema tipoProblema;

    // Observação: Ter o enum 'TipoProblema' aqui e também em 'Ticket'
    // pode levar a inconsistências. Considere movê-lo para um arquivo próprio.
    public enum TipoProblema {
        OUTRO, ELETRICO, INFRAESTRUTURA, MOBILIARIO, REDE, TECNOLOGIA
    }
}