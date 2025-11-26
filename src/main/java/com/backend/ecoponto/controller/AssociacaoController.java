package com.backend.ecoponto.controller;

import com.backend.ecoponto.dto.AssociacaoCreateDto;
import com.backend.ecoponto.dto.AssociacaoDto;
import com.backend.ecoponto.dto.AuthResponseDto;
import com.backend.ecoponto.dto.ItemDto;
import com.backend.ecoponto.service.AssociacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/associacoes")
@CrossOrigin(origins = "http://localhost:3000")
public class AssociacaoController {

    @Autowired
    private AssociacaoService associacaoService;

    @PostMapping
    public ResponseEntity<AuthResponseDto> criarAssociacao(@Valid @RequestBody AssociacaoCreateDto associacaoCreateDto) {
        AuthResponseDto response = associacaoService.createAssociacao(associacaoCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AssociacaoDto>> listarAssociacoes() {
        List<AssociacaoDto> associacoes = associacaoService.listarTodos();
        return new ResponseEntity<>(associacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociacaoDto> buscarAssociacaoPorId(@PathVariable Long id) {
        AssociacaoDto associacao = associacaoService.buscarPorId(id);
        return new ResponseEntity<>(associacao, HttpStatus.OK);
    }

    @PostMapping("/{associacaoId}/coletar/{itemId}")
    public ResponseEntity<String> coletarItem(@PathVariable Long associacaoId, @PathVariable Long itemId) {
        String resultado = associacaoService.coletarItem(associacaoId, itemId);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @GetMapping("/itens-disponiveis")
    public ResponseEntity<List<ItemDto>> listarItensDisponiveis() {
        List<ItemDto> itensDisponiveis = associacaoService.listarItensDisponiveis();
        return new ResponseEntity<>(itensDisponiveis, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociacaoDto> atualizarAssociacao(@PathVariable Long id, @Valid @RequestBody AssociacaoCreateDto associacaoCreateDto) {
        AssociacaoDto associacaoAtualizada = associacaoService.atualizar(id, associacaoCreateDto);
        return new ResponseEntity<>(associacaoAtualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssociacao(@PathVariable Long id) {
        associacaoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}