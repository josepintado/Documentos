package com.example.sistradoc.service;

import com.example.sistradoc.dto.SolicitudRequestDTO;
import com.example.sistradoc.model.Documento;
import com.example.sistradoc.model.Solicitud;
import com.example.sistradoc.model.enums.DocumentoEstado;
import com.example.sistradoc.model.enums.SolicitudEstado;
import com.example.sistradoc.model.enums.SolicitudTipo;
import com.example.sistradoc.repository.DocumentoRepository;
import com.example.sistradoc.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    public Solicitud crearSolicitud(SolicitudRequestDTO dto) {
        Documento doc = documentoRepository.findById(dto.getIdDocumento())
                .orElseThrow(() -> new RuntimeException("Documento not found"));

        if (doc.getEstado() != DocumentoEstado.APROBADO) {
            throw new IllegalStateException("Documento is not available for loan or transfer.");
        }

        Solicitud solicitud = new Solicitud();
        solicitud.setDocumento(doc);
        solicitud.setSolicitante(dto.getSolicitante());
        solicitud.setTipoSolicitud(dto.getTipoSolicitud());
        solicitud.setEstadoSolicitud(SolicitudEstado.PENDIENTE);

        return solicitudRepository.save(solicitud);
    }

    public List<Solicitud> getSolicitudesPendientes(SolicitudTipo tipo) {
        return solicitudRepository.findByEstadoSolicitud(SolicitudEstado.PENDIENTE)
                .stream()
                .filter(s -> s.getTipoSolicitud() == tipo)
                .toList();
    }

    public Solicitud aprobarSolicitud(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud not found"));

        solicitud.setEstadoSolicitud(SolicitudEstado.APROBADA);
        solicitud.setFechaAprobacion(LocalDateTime.now());

        Documento doc = solicitud.getDocumento();
        if (solicitud.getTipoSolicitud() == SolicitudTipo.PRESTAMO) {
            doc.setEstado(DocumentoEstado.EN_PRESTAMO);
        } else if (solicitud.getTipoSolicitud() == SolicitudTipo.TRANSFERENCIA) {
            doc.setEstado(DocumentoEstado.TRANSFERIDO);
        }
        documentoRepository.save(doc);

        return solicitudRepository.save(solicitud);
    }

    public Solicitud rechazarSolicitud(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud not found"));

        solicitud.setEstadoSolicitud(SolicitudEstado.RECHAZADA);
        return solicitudRepository.save(solicitud);
    }


    public Documento devolverDocumento(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud not found"));

        if (solicitud.getTipoSolicitud() != SolicitudTipo.PRESTAMO || solicitud.getEstadoSolicitud() != SolicitudEstado.APROBADA) {
            throw new IllegalStateException("This document cannot be returned.");
        }

        Documento doc = solicitud.getDocumento();
        doc.setEstado(DocumentoEstado.APROBADO);
        return documentoRepository.save(doc);
    }

    public ByteArrayInputStream generarPdfTransferencia(Long solicitudId) {
        // TODO: Implement PDF generation logic
        return null;
    }
}
