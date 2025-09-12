package com.backend.ecoponto.repository;

import com.backend.ecoponto.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DoadorRepository extends JpaRepository<Doador, Long> {
 
}