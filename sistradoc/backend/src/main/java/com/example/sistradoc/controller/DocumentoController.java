package com.example.sistradoc.controller;

import com.example.sistradoc.dto.DocumentoRegistroDTO;
import com.example.sistradoc.model.Documento;
import com.example.sistradoc.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping("/registrar")
    public ResponseEntity<Documento> registrarDocumento(@RequestPart("documento") DocumentoRegistroDTO dto,
                                                        @RequestPart(value = "file", required = false) MultipartFile file) {
        Documento nuevoDocumento = documentoService.registrarDocumento(dto, file);
        return ResponseEntity.ok(nuevoDocumento);
    }
}
