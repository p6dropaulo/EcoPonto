package com.backend.ecoponto.controller;

import com.backend.ecoponto.model.Associacao;
import com.backend.ecoponto.service.AssociacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/associacoes")
public class AssociacaoController {

    @Autowired
    private AssociacaoService associacaoService;

    @PostMapping
    public ResponseEntity<Associacao> criarAssociacao(@RequestBody Associacao associacao) {
        Associacao novaAssociacao = associacaoService.salvar(associacao);
        return new ResponseEntity<>(novaAssociacao, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Associacao>> listarAssociacoes() {
        List<Associacao> associacoes = associacaoService.listarTodos();
        return new ResponseEntity<>(associacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Associacao> buscarAssociacaoPorId(@PathVariable Long id) {
        Optional<Associacao> associacao = associacaoService.buscarPorId(id);
        return associacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Associacao> atualizarAssociacao(@PathVariable Long id, @RequestBody Associacao associacao) {
        if (!id.equals(associacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Associacao associacaoAtualizada = associacaoService.atualizar(associacao);
        if (associacaoAtualizada != null) {
            return new ResponseEntity<>(associacaoAtualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssociacao(@PathVariable Long id) {
        associacaoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}