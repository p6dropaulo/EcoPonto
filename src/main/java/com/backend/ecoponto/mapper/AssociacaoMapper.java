package com.backend.ecoponto.mapper;

import com.backend.ecoponto.dto.AssociacaoCreateDto;
import com.backend.ecoponto.dto.AssociacaoDto;
import com.backend.ecoponto.model.Associacao;
import org.springframework.stereotype.Component;

@Component
public class AssociacaoMapper {

    public Associacao toEntity(AssociacaoCreateDto dto) {
        Associacao associacao = new Associacao();
        associacao.setName(dto.getName());
        associacao.setCnpj(dto.getCnpj());
        associacao.setEmail(dto.getEmail());
        associacao.setPhone(dto.getPhone());
        associacao.setAddress(dto.getAddress());
        return associacao;
    }

    public AssociacaoDto toDto(Associacao associacao) {
        AssociacaoDto dto = new AssociacaoDto();
        dto.setId(associacao.getId());
        dto.setName(associacao.getName());
        dto.setCnpj(associacao.getCnpj());
        dto.setEmail(associacao.getEmail());
        dto.setPhone(associacao.getPhone());
        dto.setAddress(associacao.getAddress());
        return dto;
    }
}