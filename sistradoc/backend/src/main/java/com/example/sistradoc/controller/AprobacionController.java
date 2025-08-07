package com.example.sistradoc.controller;

import com.example.sistradoc.model.Documento;
import com.example.sistradoc.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aprobaciones")
public class AprobacionController {

    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/pendientes")
    public ResponseEntity<List<Documento>> getDocumentosPendientes() {
        return ResponseEntity.ok(documentoService.getDocumentosPendientes());
    }

    @PostMapping("/{documentoId}/aprobar")
    public ResponseEntity<Documento> aprobarDocumento(@PathVariable Long documentoId) {
        return ResponseEntity.ok(documentoService.aprobarDocumento(documentoId));
    }

    @PostMapping("/{documentoId}/rechazar")
    public ResponseEntity<Documento> rechazarDocumento(@PathVariable Long documentoId) {
        return ResponseEntity.ok(documentoService.rechazarDocumento(documentoId));
    }
}
