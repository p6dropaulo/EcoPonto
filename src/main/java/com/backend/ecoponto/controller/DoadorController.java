package com.backend.ecoponto.controller;

import com.backend.ecoponto.dto.DoadorCreateDto;
import com.backend.ecoponto.dto.DoadorDto;
import com.backend.ecoponto.service.DoadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doadores")
public class DoadorController {

    @Autowired
    private DoadorService doadorService;

    @PostMapping
    public ResponseEntity<DoadorDto> criarDoador(@Valid @RequestBody DoadorCreateDto doadorCreateDto) {
        DoadorDto novoDoador = doadorService.createDoador(doadorCreateDto);
        return new ResponseEntity<>(novoDoador, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoadorDto>> listarDoadores() {
        List<DoadorDto> doadores = doadorService.listarTodos();
        return new ResponseEntity<>(doadores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoadorDto> buscarDoadorPorId(@PathVariable Long id) {
        DoadorDto doador = doadorService.buscarPorId(id);
        return new ResponseEntity<>(doador, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoadorDto> atualizarDoador(@PathVariable Long id, @Valid @RequestBody DoadorCreateDto doadorCreateDto) {
        DoadorDto doadorAtualizado = doadorService.atualizar(id, doadorCreateDto);
        return new ResponseEntity<>(doadorAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDoador(@PathVariable Long id) {
        doadorService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}