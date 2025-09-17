package com.backend.ecoponto.service;

import com.backend.ecoponto.model.Doador;
import com.backend.ecoponto.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoadorService {

    @Autowired
    private DoadorRepository doadorRepository;

    public Doador salvar(Doador doador) {
        return doadorRepository.save(doador);
    }

    public Optional<Doador> buscarPorId(Long id) {
        return doadorRepository.findById(id);
    }

    public List<Doador> listarTodos() {
        return doadorRepository.findAll();
    }

    public Doador atualizar(Doador doador) {
        if (doadorRepository.existsById(doador.getId())) {
            return doadorRepository.save(doador);
        }
        return null;
    }

    public void deletar(Long id) {
        doadorRepository.deleteById(id);
    }
}