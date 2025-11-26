package com.backend.ecoponto.service;

import com.backend.ecoponto.dto.DoadorCreateDto;
import com.backend.ecoponto.dto.DoadorDto;
import com.backend.ecoponto.mapper.DoadorMapper;
import com.backend.ecoponto.model.Doador;
import com.backend.ecoponto.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoadorService {

    @Autowired
    private DoadorRepository doadorRepository;

    @Autowired
    private DoadorMapper doadorMapper;

    public DoadorDto createDoador(DoadorCreateDto dto) {
        Doador doador = doadorMapper.toEntity(dto);
        Doador doadorSalvo = doadorRepository.save(doador);
        return doadorMapper.toDto(doadorSalvo);
    }

    public DoadorDto buscarPorId(Long id) {
        Doador doador = doadorRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Doador não encontrado com ID: " + id));
        return doadorMapper.toDto(doador);
    }

    public List<DoadorDto> listarTodos() {
        List<Doador> doadores = doadorRepository.findAll();
        return doadores.stream()
                      .map(doadorMapper::toDto)
                      .collect(Collectors.toList());
    }

    public DoadorDto atualizar(Long id, DoadorCreateDto dto) {
        Doador doador = doadorRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Doador não encontrado com ID: " + id));

        doador.setNome(dto.getNome());
        doador.setEmail(dto.getEmail());
        doador.setSenha(dto.getSenha());
        doador.setTelefone(dto.getTelefone());
        doador.setCpf(dto.getCpf());

        Doador doadorAtualizado = doadorRepository.save(doador);
        return doadorMapper.toDto(doadorAtualizado);
    }

    public void deletar(Long id) {
        if (!doadorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Doador não encontrado com ID: " + id);
        }
        doadorRepository.deleteById(id);
    }
}