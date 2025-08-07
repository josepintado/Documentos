package com.example.sistradoc.repository;

import com.example.sistradoc.model.Documento;
import com.example.sistradoc.model.enums.DocumentoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    Optional<Documento> findByCorrelativo(String correlativo);
    List<Documento> findByEstado(DocumentoEstado estado);
}
