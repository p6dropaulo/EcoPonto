package com.backend.ecoponto.dto;

import lombok.Data;

@Data
public class DoadorDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
}