package com.obracerta.crud_usuario.controller;

import com.obracerta.crud_usuario.dto.TarefaRequestDTO;
import com.obracerta.crud_usuario.dto.TarefaResponseDTO;
import com.obracerta.crud_usuario.dto.TarefaUpdateQuantidadeDTO;
import com.obracerta.crud_usuario.service.TarefaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*") 
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    // POST /api/tarefas (Cria nova tarefa)
    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody TarefaRequestDTO dto) {
        try {
            TarefaResponseDTO tarefaSalva = new TarefaResponseDTO(tarefaService.criarTarefa(dto));
            return new ResponseEntity<>(tarefaSalva, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Projeto não encontrado ou erro de mapeamento
        }
    }
    
    // GET /api/tarefas/{id} (Busca por ID)
    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarTarefaPorId(@PathVariable Long id) {
        try {
            TarefaResponseDTO tarefa = tarefaService.buscarTarefaPorId(id);
            return ResponseEntity.ok(tarefa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/tarefas/projeto/{projetoId} (Lista tarefas de um projeto)
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<TarefaResponseDTO>> listarTarefasPorProjeto(@PathVariable Long projetoId) {
        try {
            List<TarefaResponseDTO> tarefas = tarefaService.listarTarefasPorProjeto(projetoId);
            return ResponseEntity.ok(tarefas);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Projeto não encontrado
        }
    }

    // PATCH /api/tarefas/{id}/concluir (Atualiza a quantidade feita)
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<TarefaResponseDTO> atualizarQuantidadeFeita(
        @PathVariable Long id, 
        @RequestBody TarefaUpdateQuantidadeDTO dto
    ) {
        try {
            TarefaResponseDTO tarefaAtualizada = tarefaService.atualizarQuantidade(id, dto.getQuantidadeFeita());
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); 
        }
    }
    
    // DELETE /api/tarefas/{id} (Deleta tarefa)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        try {
            tarefaService.deletarTarefa(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}