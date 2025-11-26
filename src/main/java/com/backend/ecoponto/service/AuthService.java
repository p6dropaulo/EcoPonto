package com.backend.ecoponto.service;

import com.backend.ecoponto.repository.AssociacaoRepository;
import com.backend.ecoponto.repository.DoadorRepository;
import com.backend.ecoponto.model.Associacao;
import com.backend.ecoponto.model.Doador;
import com.backend.ecoponto.model.TipoUsuario;
import org.springframework.web.server.ResponseStatusException;
import com.backend.ecoponto.dto.AuthResponseDto;
import com.backend.ecoponto.dto.LoginDto;
import com.backend.ecoponto.dto.UsuarioCreateDto;
import com.backend.ecoponto.model.Usuario;
import com.backend.ecoponto.repository.UsuarioRepository;
import com.backend.ecoponto.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final DoadorRepository doadorRepository;
    private final AssociacaoRepository associacaoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto registrar(UsuarioCreateDto usuarioDto) {
        // 1. Verificar se já existe usuário com este email
        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        // 2. Verificar se já existe usuário com este CPF
        if (usuarioRepository.existsByCpf(usuarioDto.getCpf())) {
            throw new RuntimeException("CPF já está cadastrado");
        }

        // 3. Criar e salvar a entidade Usuario (para o login)
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha())); // Senha criptografada
        usuario.setCpf(usuarioDto.getCpf());
        usuario.setTelefone(usuarioDto.getTelefone());
        usuario.setTipoUsuario(usuarioDto.getTipoUsuario()); // (Deve ser DOADOR)

        usuarioRepository.save(usuario); // Salva na tabela 'usuarios'

        // 4. Criar e salvar a entidade Doador (para o perfil)
        Doador doador = new Doador();
        doador.setNome(usuarioDto.getNome());
        doador.setEmail(usuarioDto.getEmail());
        doador.setSenha(passwordEncoder.encode(usuarioDto.getSenha())); // Senha criptografada (igual ao usuário)
        doador.setCpf(usuarioDto.getCpf());
        doador.setTelefone(usuarioDto.getTelefone());

        Doador doadorSalvo = doadorRepository.save(doador); // Salva na tabela 'doadores'

        // 5. Gerar token JWT (usando o Usuario)
        String token = jwtUtil.generateToken(usuario);

        // 6. Retornar o AuthResponseDto com o ID do DOADOR
        //    (Este é o ID que o ItemService precisa)
        return new AuthResponseDto(
            doadorSalvo.getId(), // <-- O ID da tabela 'doadores'
            token, 
            usuario.getNome(), 
            usuario.getEmail(), 
            usuario.getTipoUsuario()
        );
    }

    public AuthResponseDto login(LoginDto loginDto) {
        // 1. Autenticar usuário (isso busca na tabela 'usuarios')
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getSenha())
        );
    
        Usuario usuario = (Usuario) authentication.getPrincipal();
    
        // 2. Gerar token JWT
        String token = jwtUtil.generateToken(usuario);
    
        // 3. Buscar o ID do perfil correto (Doador ou Associacao)
        Long profileId; // O ID que o frontend realmente precisa
    
        if (usuario.getTipoUsuario() == TipoUsuario.DOADOR) {
            // Assumindo que DoadorRepository tem 'findByEmail'
            Doador doador = doadorRepository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário autenticado mas perfil Doador não encontrado."));
            profileId = doador.getId();
    
        } else if (usuario.getTipoUsuario() == TipoUsuario.ASSOCIACAO) {
            // Assumindo que AssociacaoRepository tem 'findByEmail'
            Associacao associacao = associacaoRepository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário autenticado mas perfil Associação não encontrado."));
            profileId = associacao.getId();
    
        } else {
            // Caso seja um tipo de usuário que não tem perfil (ex: ADMIN)
            profileId = usuario.getId(); // Retorna o próprio ID do usuário
        }
    
        // 4. Retornar o AuthResponseDto com o ID do PERFIL (Doador/Associacao)
        return new AuthResponseDto(
            profileId, // <-- O ID correto
            token, 
            usuario.getNome(), 
            usuario.getEmail(), 
            usuario.getTipoUsuario()
        );
    }
}