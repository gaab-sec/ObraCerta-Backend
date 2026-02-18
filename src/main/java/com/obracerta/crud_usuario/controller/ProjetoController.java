package com.obracerta.crud_usuario.controller;

import com.obracerta.crud_usuario.dto.ProjetoDTO; // Você vai precisar criar esse DTO se não tiver
import com.obracerta.crud_usuario.service.ProjetoService; // E esse Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos") // <--- O Front-end está procurando EXATAMENTE este endereço
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    // 1. LISTAR TODOS (GET)
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarTodos() {
        // Lógica para buscar no banco
        List<ProjetoDTO> projetos = projetoService.listarTodos();
        return ResponseEntity.ok(projetos);
    }

    // 2. CRIAR NOVO (POST)
    @PostMapping
    public ResponseEntity<ProjetoDTO> criar(@RequestBody ProjetoDTO dados) {
        ProjetoDTO novoProjeto = projetoService.criar(dados);
        return ResponseEntity.ok(novoProjeto);
    }

    // 3. ATUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> atualizar(@PathVariable Long id, @RequestBody ProjetoDTO dados) {
        ProjetoDTO projetoAtualizado = projetoService.atualizar(id, dados);
        return ResponseEntity.ok(projetoAtualizado);
    }

    // 4. DELETAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}