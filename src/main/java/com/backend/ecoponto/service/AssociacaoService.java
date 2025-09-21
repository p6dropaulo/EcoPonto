package com.backend.ecoponto.service;

import com.backend.ecoponto.model.Associacao;
import com.backend.ecoponto.repository.AssociacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssociacaoService {

    @Autowired
    private AssociacaoRepository associacaoRepository;

    public Associacao salvar(Associacao associacao) {
        return associacaoRepository.save(associacao);
    }

    public Optional<Associacao> buscarPorId(Long id) {
        return associacaoRepository.findById(id);
    }

    public List<Associacao> listarTodos() {
        return associacaoRepository.findAll();
    }

    public Associacao atualizar(Associacao associacao) {

        if (associacaoRepository.existsById(associacao.getId())) {
            return associacaoRepository.save(associacao);
        }
        return null; 
    }

    public void deletar(Long id) {
        associacaoRepository.deleteById(id);
    }
}