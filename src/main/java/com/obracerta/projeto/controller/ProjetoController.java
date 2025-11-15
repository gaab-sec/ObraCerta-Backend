package com.obracerta.projeto.controller;

import com.obracerta.projeto.model.Projeto;
import com.obracerta.projeto.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
@CrossOrigin(origins = "*") 
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    // Endpoint para criar
    @PostMapping
    public Projeto criarProjeto(@RequestBody Projeto projeto) {
        return projetoService.criarProjeto(projeto);
    }

    // Endpoint para listar td
    @GetMapping
    public List<Projeto> listarTodosProjetos() {
        return projetoService.listarTodosProjetos();
    }

    // Endpoint buscar p/ id
    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarProjetoPorId(@PathVariable Long id) {
        return projetoService.buscarProjetoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para att
    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto detalhesProjeto) {
        try {
            Projeto projetoAtualizado = projetoService.atualizarProjeto(id, detalhesProjeto);
            return ResponseEntity.ok(projetoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        try {
            projetoService.deletarProjeto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}