package com.backend.ecoponto.controller;

import com.backend.ecoponto.model.Doador;
import com.backend.ecoponto.service.DoadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doadores")
public class DoadorController {

    @Autowired
    private DoadorService doadorService;

    @PostMapping
    public ResponseEntity<Doador> criarDoador(@RequestBody Doador doador) {
        Doador novoDoador = doadorService.salvar(doador);
        return new ResponseEntity<>(novoDoador, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Doador>> listarDoadores() {
        List<Doador> doadores = doadorService.listarTodos();
        return new ResponseEntity<>(doadores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> buscarDoadorPorId(@PathVariable Long id) {
        Optional<Doador> doador = doadorService.buscarPorId(id);
        return doador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doador> atualizarDoador(@PathVariable Long id, @RequestBody Doador doador) {
        if (!id.equals(doador.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Doador doadorAtualizado = doadorService.atualizar(doador);
        if (doadorAtualizado != null) {
            return new ResponseEntity<>(doadorAtualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDoador(@PathVariable Long id) {
        doadorService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}