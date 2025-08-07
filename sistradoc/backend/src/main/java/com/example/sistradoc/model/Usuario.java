package com.example.sistradoc.model;

import com.example.sistradoc.model.enums.UsuarioRol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UsuarioRol rol;
}
