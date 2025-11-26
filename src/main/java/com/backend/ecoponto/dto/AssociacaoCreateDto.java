package com.backend.ecoponto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AssociacaoCreateDto {
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;
    
    @NotBlank(message = "Telefone é obrigatório")
    private String phone;
    
    @NotBlank(message = "Endereço é obrigatório")
    private String address;
}