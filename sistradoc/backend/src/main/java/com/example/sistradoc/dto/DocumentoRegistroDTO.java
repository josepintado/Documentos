package com.example.sistradoc.dto;

import com.example.sistradoc.model.enums.DocumentoTipo;
import lombok.Data;

@Data
public class DocumentoRegistroDTO {
    private String asunto;
    private DocumentoTipo tipo;
    private boolean esDigital;
    private String ubicacionFisica;
    private Long idAreaUsuaria;
}
