package com.backend.ecoponto.service;

import com.backend.ecoponto.dto.ItemCreateDto;
import com.backend.ecoponto.dto.ItemDto;
import com.backend.ecoponto.mapper.ItemMapper;
import com.backend.ecoponto.model.Doador;
import com.backend.ecoponto.model.Item;
import com.backend.ecoponto.repository.DoadorRepository;
import com.backend.ecoponto.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private DoadorRepository doadorRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private ItemCreateDto itemCreateDto;
    private Doador doador;
    private Item item;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        itemCreateDto = new ItemCreateDto();
        itemCreateDto.setMaterial("Plástico");
        itemCreateDto.setPesoEmKg(2.5);
        itemCreateDto.setQtdVolume(10);
        itemCreateDto.setEnderecoRetirada("Rua das Flores, 123");
        itemCreateDto.setDoadorId(1L);

        doador = new Doador();
        doador.setId(1L);
        doador.setNome("João Silva");
        doador.setEmail("joao@email.com");

        item = new Item();
        item.setId(1L);
        item.setMaterial("Plástico");
        item.setPesoEmKg(2.5);
        item.setQtdVolume(10);
        item.setEnderecoRetirada("Rua das Flores, 123");
        item.setIdDoador(1L);
        item.setStatus("DISPONIVEL");
        item.setDataRegistro(LocalDateTime.now());

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
    void createItem_Success() {
        // Arrange
        when(doadorRepository.findById(1L)).thenReturn(Optional.of(doador));
        when(itemMapper.toEntity(itemCreateDto)).thenReturn(item);
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemMapper.toDto(item)).thenReturn(itemDto);

        // Act
        ItemDto result = itemService.createItem(itemCreateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Plástico", result.getMaterial());
        assertEquals(2.5, result.getPesoEmKg());
        assertEquals(1L, result.getDoadorId());
        assertEquals("DISPONIVEL", result.getStatus());

        verify(doadorRepository).findById(1L);
        verify(itemRepository).save(any(Item.class));
        verify(itemMapper).toEntity(itemCreateDto);
        verify(itemMapper).toDto(item);
    }

    @Test
    void createItem_DoadorNotFound_ThrowsException() {
        // Arrange
        when(doadorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> itemService.createItem(itemCreateDto)
        );

        assertEquals("Doador não encontrado com ID: 1", exception.getReason());
        verify(doadorRepository).findById(1L);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void buscarPorId_Success() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemMapper.toDto(item)).thenReturn(itemDto);

        // Act
        ItemDto result = itemService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Plástico", result.getMaterial());

        verify(itemRepository).findById(1L);
        verify(itemMapper).toDto(item);
    }

    @Test
    void buscarPorId_ItemNotFound_ThrowsException() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> itemService.buscarPorId(1L)
        );

        assertEquals("Item não encontrado com ID: 1", exception.getReason());
        verify(itemRepository).findById(1L);
    }
}