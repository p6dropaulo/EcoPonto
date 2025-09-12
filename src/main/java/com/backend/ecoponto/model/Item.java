package com.backend.ecoponto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itens")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String material;
    private Double pesoEmKg;
    private Integer qtdVolume; 
    private String urlFoto;
    private String EnderecoRetirada; 
    private Long idDoador;
    private String status; 
    private LocalDateTime dataRegistro; 
}