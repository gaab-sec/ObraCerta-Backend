package com.obracerta.crud_usuario.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obracerta.crud_usuario.dto.LoginDTO;
import com.obracerta.crud_usuario.dto.UsuarioCadastroDTO;
import com.obracerta.crud_usuario.dto.UsuarioResponseDTO;
import com.obracerta.crud_usuario.dto.UsuarioUpdateDTO;
import com.obracerta.crud_usuario.model.Usuario;
import com.obracerta.crud_usuario.service.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import org.springframework.security.core.context.SecurityContextHolder; // <-- ADICIONE ESTE IMPORT
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioCadastroDTO dto ){
        try {
            Usuario usuarioSalvo = usuarioService.cadastrar(dto);
            // Retorna o usuário salvo com status 201 (Created)
            return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Retorna 400 (Bad Request) se o email já existir (ou outra falha)
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

            Authentication auth = authenticationManager.authenticate(usernamePassword); 

            var context = SecurityContextHolder.createEmptyContext(); 
            context.setAuthentication(auth); 
            request.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            Usuario usuarioLogado = (Usuario) auth.getPrincipal();
            UsuarioResponseDTO resposta = new UsuarioResponseDTO(usuarioLogado);

            return ResponseEntity.ok(resposta);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED); 
        }
    }

    // NOVO: Endpoint de Update
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, dto);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // NOVO: Endpoint de Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return new ResponseEntity<>("Usuário deletado com sucesso", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosUsuarios() { 
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // NOVO: Endpoint GET para Buscar um Usuário por ID
    // (GET /api/usuarios/{id})
    // Note que este @GetMapping("/{id}") é DIFERENTE do @PutMapping("/{id}")
    // e @DeleteMapping("/{id}") porque o *verbo* HTTP é diferente.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Retorna 404 (Not Found) se o service lançar a exceção
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
        }
    }       
}