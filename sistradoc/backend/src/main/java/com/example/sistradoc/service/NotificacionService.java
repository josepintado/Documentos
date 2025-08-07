package com.example.sistradoc.service;

import com.example.sistradoc.model.Area;
import com.example.sistradoc.repository.AreaRepository;
import com.example.sistradoc.repository.DocumentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);
    private static final double MIN_DIGITALIZACION_PORCENTAJE = 95.0;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Scheduled(cron = "0 0 1 * * ?") // Run every day at 1 AM
    public void verificarNivelDigitalizacion() {
        log.info("Executing scheduled task to check digitalization levels...");

        List<Area> areas = areaRepository.findAll();
        for (Area area : areas) {
            List<Documento> documentosDelArea = documentoRepository.findAll().stream()
                    .filter(d -> d.getIdAreaUsuaria().equals(area)).toList();

            long totalDocumentos = documentosDelArea.size();
            if (totalDocumentos == 0) {
                continue; // Skip areas with no documents
            }

            long documentosDigitales = documentosDelArea.stream().filter(Documento::isEsDigital).count();

            double porcentaje = (double) documentosDigitales / totalDocumentos * 100;
            area.setPorcentajeDigitalizacion(porcentaje);
            areaRepository.save(area);

            if (porcentaje < MIN_DIGITALIZACION_PORCENTAJE) {
                log.warn("Area '{}' is below the digitalization threshold of {}%. Current: {:.2f}%",
                        area.getNombre(), MIN_DIGITALIZACION_PORCENTAJE, porcentaje);
                // In a real app, you would create a Notification entity or send an email.
            }
        }
        log.info("Finished digitalization check.");
    }
}
