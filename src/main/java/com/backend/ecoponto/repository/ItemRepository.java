package com.backend.ecoponto.repository;

import com.backend.ecoponto.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    List<Item> findByIdDoador(Long idDoador);
    
    List<Item> findByStatus(String status);
}