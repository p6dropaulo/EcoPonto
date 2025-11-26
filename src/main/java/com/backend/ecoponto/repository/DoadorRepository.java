package com.backend.ecoponto.repository;

import com.backend.ecoponto.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- 1. ADICIONE ESTE IMPORT

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {
    // 2. ADICIONE ESTA LINHA
    Optional<Doador> findByEmail(String email);
}