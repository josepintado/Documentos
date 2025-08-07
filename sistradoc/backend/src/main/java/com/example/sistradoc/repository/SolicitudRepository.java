package com.example.sistradoc.repository;

import com.example.sistradoc.model.Solicitud;
import com.example.sistradoc.model.enums.SolicitudEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstadoSolicitud(SolicitudEstado estado);
}
