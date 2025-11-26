package com.backend.ecoponto.controller;

import com.backend.ecoponto.dto.ItemCreateDto;
import com.backend.ecoponto.dto.ItemDto;
import com.backend.ecoponto.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    private ItemCreateDto itemCreateDto;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        itemCreateDto = new ItemCreateDto();
        itemCreateDto.setMaterial("Plástico");
        itemCreateDto.setPesoEmKg(2.5);
        itemCreateDto.setQtdVolume(10);
        itemCreateDto.setEnderecoRetirada("Rua das Flores, 123");
        itemCreateDto.setDoadorId(1L);

        itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setMaterial("Plástico");
        itemDto.setPesoEmKg(2.5);
        itemDto.setQtdVolume(10);
        itemDto.setEnderecoRetirada("Rua das Flores, 123");
        itemDto.setDoadorId(1L);
        itemDto.setStatus("DISPONIVEL");
        itemDto.setDataRegistro(LocalDateTime.now());
    }

    @Test
    void criarItem_Success() throws Exception {
        // Arrange
        when(itemService.createItem(any(ItemCreateDto.class))).thenReturn(itemDto);

        // Act & Assert
        mockMvc.perform(post("/api/itens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.material").value("Plástico"))
                .andExpect(jsonPath("$.pesoEmKg").value(2.5))
                .andExpect(jsonPath("$.doadorId").value(1L))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));
    }

    @Test
    void criarItem_ValidationError() throws Exception {
        // Arrange - DTO inválido (sem material)
        itemCreateDto.setMaterial("");

        // Act & Assert
        mockMvc.perform(post("/api/itens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarItens_Success() throws Exception {
        // Arrange
        List<ItemDto> itens = Arrays.asList(itemDto);
        when(itemService.listarTodos()).thenReturn(itens);

        // Act & Assert
        mockMvc.perform(get("/api/itens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].material").value("Plástico"));
    }

    @Test
    void buscarItemPorId_Success() throws Exception {
        // Arrange
        when(itemService.buscarPorId(1L)).thenReturn(itemDto);

        // Act & Assert
        mockMvc.perform(get("/api/itens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.material").value("Plástico"));
    }
}