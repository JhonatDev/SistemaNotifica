package com.Notifica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    //hash
    //@Column(nullable = false)
    private String hash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario; // Novo campo para o tipo de usuário

    // Enum para definir o tipo de usuário
    public enum TipoUsuario {
        ADMIN(1), Usuarios(2);

        private final int codigo;

        TipoUsuario(int codigo) {
            this.codigo = codigo;
        }

        public int getCodigo() {
            return codigo;
        }
    }
}


