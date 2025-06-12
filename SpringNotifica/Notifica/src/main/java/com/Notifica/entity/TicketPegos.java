package com.Notifica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
// import java.time.LocalDateTime; // Não é mais necessário aqui

@Entity
@Data
public class TicketPegos extends Auditable { // 1. Herda de Auditable
    @Id
    private Long id; // id do ticket pego igual ao id do ticket

    // id do usuario que pegou o ticket
    @NotBlank(message = "O id do usuario que pegou o ticket não pode ser vazio")
    private Long idUsuarioPego;

    // nome do usuario que pegou o ticket
    @NotBlank(message = "O nome do usuario que pegou o ticket não pode ser vazio")
    private String nomeUsuarioPego;

    // Agora, os campos 'createdBy' e 'createdDate' registrarão
    // QUEM pegou o ticket e QUANDO, automaticamente.
}