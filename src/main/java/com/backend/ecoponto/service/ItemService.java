package com.backend.ecoponto.service;

import com.backend.ecoponto.dto.ItemCreateDto;
import com.backend.ecoponto.dto.ItemDto;
import com.backend.ecoponto.mapper.ItemMapper;
import com.backend.ecoponto.model.Item;
import com.backend.ecoponto.model.Doador;
import com.backend.ecoponto.repository.ItemRepository;
import com.backend.ecoponto.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DoadorRepository doadorRepository;

    @Autowired
    private ItemMapper itemMapper;

    public ItemDto createItem(ItemCreateDto dto) {
        // Buscar o doador no banco de dados
        Doador doador = doadorRepository.findById(dto.getDoadorId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Doador não encontrado com ID: " + dto.getDoadorId()));

        // Converter DTO para entidade
        Item item = itemMapper.toEntity(dto);
        item.setDataRegistro(LocalDateTime.now());
        item.setStatus("DISPONIVEL");

        // Salvar o item
        Item itemSalvo = itemRepository.save(item);
        
        return itemMapper.toDto(itemSalvo);
    }

    public List<ItemDto> buscarPorDoador(Long doadorId) {
        List<Item> itens = itemRepository.findByIdDoador(doadorId);
        return itens.stream()
                   .map(itemMapper::toDto)
                   .collect(Collectors.toList());
    }

    public ItemDto buscarPorId(Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Item não encontrado com ID: " + id));
        return itemMapper.toDto(item);
    }

    public List<ItemDto> listarTodos() {
        List<Item> itens = itemRepository.findAll();
        return itens.stream()
                   .map(itemMapper::toDto)
                   .collect(Collectors.toList());
    }

    public ItemDto atualizar(Long id, ItemCreateDto dto) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Item não encontrado com ID: " + id));

        // Verificar se o doador existe
        doadorRepository.findById(dto.getDoadorId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Doador não encontrado com ID: " + dto.getDoadorId()));

        // Atualizar campos
        item.setMaterial(dto.getMaterial());
        item.setPesoEmKg(dto.getPesoEmKg());
        item.setQtdVolume(dto.getQtdVolume());
        item.setUrlFoto(dto.getUrlFoto());
        item.setEnderecoRetirada(dto.getEnderecoRetirada());
        item.setIdDoador(dto.getDoadorId());

        Item itemAtualizado = itemRepository.save(item);
        return itemMapper.toDto(itemAtualizado);
    }

    public void deletar(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Item não encontrado com ID: " + id);
        }
        itemRepository.deleteById(id);
    }
}