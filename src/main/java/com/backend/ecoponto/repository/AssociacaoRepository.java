package com.backend.ecoponto.repository;

import com.backend.ecoponto.model.Associacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- 1. ADICIONE ESTE IMPORT

@Repository
public interface AssociacaoRepository extends JpaRepository<Associacao, Long> {

    // 2. ADICIONE ESTA LINHA
    Optional<Associacao> findByEmail(String email);
}