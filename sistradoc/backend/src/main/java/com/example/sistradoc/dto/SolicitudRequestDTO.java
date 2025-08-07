package com.example.sistradoc.dto;

import com.example.sistradoc.model.enums.SolicitudTipo;
import lombok.Data;

@Data
public class SolicitudRequestDTO {
    private Long idDocumento;
    private String solicitante;
    private SolicitudTipo tipoSolicitud;
}
