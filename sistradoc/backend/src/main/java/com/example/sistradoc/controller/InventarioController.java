package com.example.sistradoc.controller;

import com.example.sistradoc.model.Documento;
import com.example.sistradoc.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/")
    public ResponseEntity<Page<Documento>> getInventario(Pageable pageable) {
        return ResponseEntity.ok(documentoService.getInventario(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> getDocumentoById(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.getDocumentoById(id));
    }

    @GetMapping("/formato-inventario")
    public ResponseEntity<?> getFormatoInventario() {
        // PDF generation logic will be added later
        return ResponseEntity.ok("PDF generation not implemented yet.");
    }
}
