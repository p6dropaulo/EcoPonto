package com.backend.ecoponto.controller;

import com.backend.ecoponto.dto.ItemCreateDto;
import com.backend.ecoponto.dto.ItemDto;
import com.backend.ecoponto.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> criarItem(@Valid @RequestBody ItemCreateDto itemCreateDto) {
        ItemDto novoItem = itemService.createItem(itemCreateDto);
        return new ResponseEntity<>(novoItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> listarItens() {
        List<ItemDto> itens = itemService.listarTodos();
        return new ResponseEntity<>(itens, HttpStatus.OK);
    }

    @GetMapping("/doador/{doadorId}")
    public ResponseEntity<List<ItemDto>> listarItensPorDoador(@PathVariable Long doadorId) {
        List<ItemDto> itens = itemService.buscarPorDoador(doadorId);
        return new ResponseEntity<>(itens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> buscarItemPorId(@PathVariable Long id) {
        ItemDto item = itemService.buscarPorId(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> atualizarItem(@PathVariable Long id, @Valid @RequestBody ItemCreateDto itemCreateDto) {
        ItemDto itemAtualizado = itemService.atualizar(id, itemCreateDto);
        return new ResponseEntity<>(itemAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}