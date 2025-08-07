package com.example.sistradoc.controller;

import com.example.sistradoc.dto.SolicitudRequestDTO;
import com.example.sistradoc.model.Documento;
import com.example.sistradoc.model.Solicitud;
import com.example.sistradoc.model.enums.SolicitudTipo;
import com.example.sistradoc.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping("/solicitar")
    public ResponseEntity<Solicitud> solicitarPrestamo(@RequestBody SolicitudRequestDTO dto) {
        dto.setTipoSolicitud(SolicitudTipo.PRESTAMO);
        return ResponseEntity.ok(solicitudService.crearSolicitud(dto));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Solicitud>> getPrestamosPendientes() {
        return ResponseEntity.ok(solicitudService.getSolicitudesPendientes(SolicitudTipo.PRESTAMO));
    }

    @PostMapping("/{solicitudId}/aprobar")
    public ResponseEntity<Solicitud> aprobarPrestamo(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(solicitudService.aprobarSolicitud(solicitudId));
    }

    @PostMapping("/{solicitudId}/rechazar")
    public ResponseEntity<Solicitud> rechazarPrestamo(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(solicitudService.rechazarSolicitud(solicitudId));
    }

    @PostMapping("/{solicitudId}/devolver")
    public ResponseEntity<Documento> devolverDocumento(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(solicitudService.devolverDocumento(solicitudId));
    }
}
