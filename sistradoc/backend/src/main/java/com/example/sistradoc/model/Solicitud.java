package com.example.sistradoc.model;

import com.example.sistradoc.model.enums.SolicitudEstado;
import com.example.sistradoc.model.enums.SolicitudTipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SolicitudTipo tipoSolicitud;

    @Enumerated(EnumType.STRING)
    private SolicitudEstado estadoSolicitud;

    private String solicitante;

    @CreationTimestamp
    private LocalDateTime fechaSolicitud;

    private LocalDateTime fechaAprobacion;

    @ManyToOne
    @JoinColumn(name = "id_documento")
    private Documento documento;
}
