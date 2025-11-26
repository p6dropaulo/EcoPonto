package com.backend.ecoponto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemCreateDto {
    
    @NotBlank(message = "Material é obrigatório")
    private String material;
    
    @NotNull(message = "Peso é obrigatório")
    @Positive(message = "Peso deve ser positivo")
    private Double pesoEmKg;
    
    @NotNull(message = "Quantidade de volume é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer qtdVolume;
    
    private String urlFoto;
    
    @NotBlank(message = "Endereço de retirada é obrigatório")
    private String enderecoRetirada;
    
    @NotNull(message = "ID do doador é obrigatório")
    private Long doadorId;
}