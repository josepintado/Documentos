package com.example.sistradoc.controller;

import com.example.sistradoc.dto.SolicitudRequestDTO;
import com.example.sistradoc.model.Solicitud;
import com.example.sistradoc.model.enums.SolicitudTipo;
import com.example.sistradoc.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping("/solicitar")
    public ResponseEntity<Solicitud> solicitarTransferencia(@RequestBody SolicitudRequestDTO dto) {
        dto.setTipoSolicitud(SolicitudTipo.TRANSFERENCIA);
        return ResponseEntity.ok(solicitudService.crearSolicitud(dto));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Solicitud>> getTransferenciasPendientes() {
        return ResponseEntity.ok(solicitudService.getSolicitudesPendientes(SolicitudTipo.TRANSFERENCIA));
    }

    @PostMapping("/{solicitudId}/aprobar")
    public ResponseEntity<Solicitud> aprobarTransferencia(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(solicitudService.aprobarSolicitud(solicitudId));
    }

    @PostMapping("/{solicitudId}/rechazar")
    public ResponseEntity<Solicitud> rechazarTransferencia(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(solicitudService.rechazarSolicitud(solicitudId));
    }

    @GetMapping("/{solicitudId}/formato")
    public ResponseEntity<?> getFormatoTransferencia(@PathVariable Long solicitudId) {
        // PDF generation logic will be added later
        return ResponseEntity.ok("PDF generation for transfer not implemented yet.");
    }
}
