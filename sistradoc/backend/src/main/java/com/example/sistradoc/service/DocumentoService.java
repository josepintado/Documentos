package com.example.sistradoc.service;

import com.example.sistradoc.dto.DocumentoRegistroDTO;
import com.example.sistradoc.model.Area;
import com.example.sistradoc.model.Documento;
import com.example.sistradoc.model.enums.DocumentoEstado;
import com.example.sistradoc.repository.AreaRepository;
import com.example.sistradoc.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private AreaRepository areaRepository;

    // In a real application, this would be a more robust file storage service (e.g., S3)
    private final Path fileStorageLocation = Path.of("uploads");

    public Documento registrarDocumento(DocumentoRegistroDTO dto, MultipartFile file) {
        Area area = areaRepository.findById(dto.getIdAreaUsuaria())
                .orElseThrow(() -> new RuntimeException("Area not found"));

        Documento doc = new Documento();
        doc.setAsunto(dto.getAsunto());
        doc.setTipo(dto.getTipo());
        doc.setEsDigital(dto.isEsDigital());
        doc.setUbicacionFisica(dto.getUbicacionFisica());
        doc.setIdAreaUsuaria(area);
        doc.setEstado(DocumentoEstado.PENDIENTE_APROBACION);
        // Generate a unique correlativo
        doc.setCorrelativo("DOC-" + UUID.randomUUID().toString().substring(0, 8));

        if (dto.isEsDigital() && file != null && !file.isEmpty()) {
            // In a real app, save the file and set the path
            // For now, we'll just store a placeholder
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            doc.setRutaArchivoDigital(fileStorageLocation.resolve(fileName).toString());
        }

        return documentoRepository.save(doc);
    }

    public List<Documento> getDocumentosPendientes() {
        return documentoRepository.findByEstado(DocumentoEstado.PENDIENTE_APROBACION);
    }

    public Documento aprobarDocumento(Long documentoId) {
        Documento doc = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento not found"));
        doc.setEstado(DocumentoEstado.APROBADO);
        return documentoRepository.save(doc);
    }

    public Documento rechazarDocumento(Long documentoId) {
        Documento doc = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento not found"));
        doc.setEstado(DocumentoEstado.RECHAZADO);
        return documentoRepository.save(doc);
    }

    public Page<Documento> getInventario(Pageable pageable) {
        return documentoRepository.findAll(pageable);
    }

    public Documento getDocumentoById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento not found"));
    }

    public ByteArrayInputStream generarPdfInventario() {
        // TODO: Implement PDF generation logic
        return null;
    }
}
