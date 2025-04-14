package com.Notifica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class TicketPegos {
    @Id
    private Long id; // id do ticket pego igual ao id do ticket

    //id do usuario que pegou o ticket
    @NotBlank(message = "O id do usuario que pegou o ticket não pode ser vazio")
    private Long idUsuarioPego;

    //nome do usuario que pegou o ticket
    @NotBlank(message = "O nome do usuario que pegou o ticket não pode ser vazio")
    private String nomeUsuarioPego;

}
