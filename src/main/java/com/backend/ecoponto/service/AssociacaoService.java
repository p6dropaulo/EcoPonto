package com.backend.ecoponto.service;

import com.backend.ecoponto.dto.AuthResponseDto;
import com.backend.ecoponto.model.Usuario;
import com.backend.ecoponto.model.TipoUsuario;
import com.backend.ecoponto.dto.AssociacaoCreateDto;
import com.backend.ecoponto.dto.AssociacaoDto;
import com.backend.ecoponto.dto.ItemDto;
import com.backend.ecoponto.mapper.AssociacaoMapper;
import com.backend.ecoponto.mapper.ItemMapper;
import com.backend.ecoponto.model.Associacao;
import com.backend.ecoponto.model.Item;
import com.backend.ecoponto.repository.AssociacaoRepository;
import com.backend.ecoponto.repository.ItemRepository;
import com.backend.ecoponto.repository.UsuarioRepository;
import com.backend.ecoponto.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociacaoService {

    @Autowired
    private AssociacaoRepository associacaoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AssociacaoMapper associacaoMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository; // (Confirme se o nome é este)

    @Autowired
    private JwtUtil jwtUtil; // (Confirme se o nome é este

    public AuthResponseDto createAssociacao(AssociacaoCreateDto dto) {
        // 1. Verificar se o usuário já existe
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está em uso!");
        }

        // 2. Criar e salvar a entidade Usuario
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        novoUsuario.setNome(dto.getName()); // Usando 'name' do AssociacaoCreateDto para o 'nome' do Usuario
        novoUsuario.setTelefone(dto.getPhone());
        novoUsuario.setTipoUsuario(TipoUsuario.ASSOCIACAO); // Define o tipo de usuário

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // 3. Criar e salvar a entidade Associacao
        Associacao associacao = associacaoMapper.toEntity(dto);
        
        // A linha "associacao.setUsuario(usuarioSalvo);" foi removida,
        // pois a entidade Associacao não possui este campo.
        
        associacaoRepository.save(associacao);

        // 4. Gerar o Token
        // CORREÇÃO: Passamos o objeto 'usuarioSalvo' (que deve implementar UserDetails)
        String token = jwtUtil.generateToken(usuarioSalvo);

        // 5. Retornar o AuthResponseDto
        return new AuthResponseDto(
            usuarioSalvo.getId(),
            token,
            usuarioSalvo.getNome(),
            usuarioSalvo.getEmail(),
            usuarioSalvo.getTipoUsuario()
        );
    }

    public AssociacaoDto buscarPorId(Long id) {
        Associacao associacao = associacaoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Associação não encontrada com ID: " + id));
        return associacaoMapper.toDto(associacao);
    }

    public List<AssociacaoDto> listarTodos() {
        List<Associacao> associacoes = associacaoRepository.findAll();
        return associacoes.stream()
                         .map(associacaoMapper::toDto)
                         .collect(Collectors.toList());
    }

    public String coletarItem(Long associacaoId, Long itemId) {
        // Verificar se a associação existe
        Associacao associacao = associacaoRepository.findById(associacaoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Associação não encontrada com ID: " + associacaoId));

        // Verificar se o item existe
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Item não encontrado com ID: " + itemId));

        // Verificar se o item está disponível para coleta
        if (!"DISPONIVEL".equals(item.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Item não está disponível para coleta. Status atual: " + item.getStatus());
        }

        // Atualizar o status do item para coletado
        item.setStatus("COLETADO");
        itemRepository.save(item);

        return "Item " + itemId + " foi coletado com sucesso pela associação " + associacao.getName();
    }

    public List<ItemDto> listarItensDisponiveis() {
        List<Item> itens = itemRepository.findByStatus("DISPONIVEL");
        return itens.stream()
                   .map(itemMapper::toDto)
                   .collect(Collectors.toList());
    }

    public AssociacaoDto atualizar(Long id, AssociacaoCreateDto dto) {
        Associacao associacao = associacaoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Associação não encontrada com ID: " + id));

        associacao.setName(dto.getName());
        associacao.setCnpj(dto.getCnpj());
        associacao.setEmail(dto.getEmail());
        associacao.setPhone(dto.getPhone());
        associacao.setAddress(dto.getAddress());

        Associacao associacaoAtualizada = associacaoRepository.save(associacao);
        return associacaoMapper.toDto(associacaoAtualizada);
    }

    public void deletar(Long id) {
        if (!associacaoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Associação não encontrada com ID: " + id);
        }
        associacaoRepository.deleteById(id);
    }
}