package com.obracerta.crud_usuario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.obracerta.crud_usuario.dto.UsuarioCadastroDTO;
import com.obracerta.crud_usuario.dto.UsuarioResponseDTO;
import com.obracerta.crud_usuario.dto.UsuarioUpdateDTO;
import com.obracerta.crud_usuario.model.Usuario;
import com.obracerta.crud_usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario cadastrar(UsuarioCadastroDTO dto) {
        
        // 1. Regra de negócio: Verificar se o email já existe
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            // Em um app real, lançaríamos uma exceção customizada
            throw new RuntimeException("Email já cadastrado");
        }

        // 2. Fazer o HASH da senha
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        // 3. Criar o novo usuário
        Usuario novoUsuario = new Usuario(dto.nome(), dto.email(), senhaCriptografada);

        // 4. Salvar no banco
        return usuarioRepository.save(novoUsuario);
    }

    public Usuario atualizar(Long id, UsuarioUpdateDTO dto){

        // 1. Busca o usuário pelo ID
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Regra de negócio: Verifica se o novo email já está em uso por OUTRO usuário
        if (!dto.email().equals(usuarioExistente.getEmail())) {
            if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
                throw new RuntimeException("Email já cadastrado em outra conta");
            }
        }
        
        // 3. Atualiza os dados
        usuarioExistente.setNome(dto.nome());
        usuarioExistente.setEmail(dto.email());
        // Não atualizamos a senha aqui

        // 4. Salva (o JPA entende que é um update por causa do ID)
        return usuarioRepository.save(usuarioExistente);    
    }

    public void deletar(Long id){
        if (!usuarioRepository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioResponseDTO> listarTodos(){
        return usuarioRepository.findAll()
            .stream()
            .map(UsuarioResponseDTO::new)
            .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        return new UsuarioResponseDTO(usuario);
    }
        

}