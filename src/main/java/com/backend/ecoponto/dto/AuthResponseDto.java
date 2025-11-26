package com.backend.ecoponto.dto;

import com.backend.ecoponto.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private Long id; // <-- 1. ADICIONE ESTA LINHA
    private String token;
    private String tipo = "Bearer";
    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;

    // 2. ATUALIZE ESTE CONSTRUTOR
    public AuthResponseDto(Long id, String token, String nome, String email, TipoUsuario tipoUsuario) {
        this.id = id;
        this.token = token;
        this.nome = nome;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }
}