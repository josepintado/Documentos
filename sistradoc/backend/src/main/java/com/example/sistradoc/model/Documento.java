package com.example.sistradoc.model;

import com.example.sistradoc.model.enums.DocumentoEstado;
import com.example.sistradoc.model.enums.DocumentoTipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
@Getter
@Setter
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String correlativo;

    private String asunto;

    @Enumerated(EnumType.STRING)
    private DocumentoTipo tipo;

    @Enumerated(EnumType.STRING)
    private DocumentoEstado estado;

    private boolean esDigital;

    private String rutaArchivoDigital;

    private String ubicacionFisica;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_area_usuaria")
    private Area idAreaUsuaria;
}
