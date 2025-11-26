package com.backend.ecoponto.mapper;

import com.backend.ecoponto.dto.ItemCreateDto;
import com.backend.ecoponto.dto.ItemDto;
import com.backend.ecoponto.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(ItemCreateDto dto) {
        Item item = new Item();
        item.setMaterial(dto.getMaterial());
        item.setPesoEmKg(dto.getPesoEmKg());
        item.setQtdVolume(dto.getQtdVolume());
        item.setUrlFoto(dto.getUrlFoto());
        item.setEnderecoRetirada(dto.getEnderecoRetirada());
        item.setIdDoador(dto.getDoadorId());
        return item;
    }

    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setMaterial(item.getMaterial());
        dto.setPesoEmKg(item.getPesoEmKg());
        dto.setQtdVolume(item.getQtdVolume());
        dto.setUrlFoto(item.getUrlFoto());
        dto.setEnderecoRetirada(item.getEnderecoRetirada());
        dto.setDoadorId(item.getIdDoador());
        dto.setStatus(item.getStatus());
        dto.setDataRegistro(item.getDataRegistro());
        return dto;
    }
}