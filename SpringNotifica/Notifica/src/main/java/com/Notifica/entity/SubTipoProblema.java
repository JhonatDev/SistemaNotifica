package com.Notifica.entity;

import com.Notifica.entity.Ticket.TipoProblema;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data // Adicionando a anotação @Data para gerar getters, setters e outros métodos
public class SubTipoProblema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O SubtipoProblema é obrigatório")
    @Column(nullable = false)
    private String subtipoProblema; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProblema tipoProblema;

    // Enumerações para TipoProblema
    public enum TipoProblema {
        OUTRO, ELETRICO, INFRAESTRUTURA, MOBILIARIO, REDE, TECNOLOGIA
    }

}
