package com.backend.ecoponto.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ItemDto {
    private Long id;
    private String material;
    private Double pesoEmKg;
    private Integer qtdVolume;
    private String urlFoto;
    private String enderecoRetirada;
    private Long doadorId;
    private String status;
    private LocalDateTime dataRegistro;
}