package com.backend.ecoponto.mapper;

import com.backend.ecoponto.dto.DoadorCreateDto;
import com.backend.ecoponto.dto.DoadorDto;
import com.backend.ecoponto.model.Doador;
import org.springframework.stereotype.Component;

@Component
public class DoadorMapper {

    public Doador toEntity(DoadorCreateDto dto) {
        Doador doador = new Doador();
        doador.setNome(dto.getNome());
        doador.setEmail(dto.getEmail());
        doador.setSenha(dto.getSenha());
        doador.setTelefone(dto.getTelefone());
        doador.setCpf(dto.getCpf());
        return doador;
    }

    public DoadorDto toDto(Doador doador) {
        DoadorDto dto = new DoadorDto();
        dto.setId(doador.getId());
        dto.setNome(doador.getNome());
        dto.setEmail(doador.getEmail());
        dto.setTelefone(doador.getTelefone());
        dto.setCpf(doador.getCpf());
        return dto;
    }
}