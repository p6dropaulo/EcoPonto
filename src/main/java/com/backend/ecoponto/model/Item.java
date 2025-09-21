package com.backend.ecoponto.model;

import jakarta.persistence.*;
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
    private String enderecoRetirada; 
    private String status;
    private LocalDateTime dataRegistro;

    @ManyToOne
    @JoinColumn(name = "doador_id", nullable = false)
    private Doador doador;

    @ManyToOne
    @JoinColumn(name = "associacao_id")
    private Associacao associacao;
}
