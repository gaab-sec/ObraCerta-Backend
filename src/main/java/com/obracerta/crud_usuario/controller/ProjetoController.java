package com.obracerta.crud_usuario.controller;
 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obracerta.crud_usuario.dto.ProjetoDTO;
import com.obracerta.crud_usuario.service.ProjetoService;
 
@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {
 
    @Autowired
    private ProjetoService projetoService;
 
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ProjetoDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(projetoService.listarPorUsuario(usuarioId));
    }
 
    @GetMapping
    public ResponseEntity<Object> listarTodos() {
        return ResponseEntity.ok(projetoService.listarTodos());
    }
 
    @PostMapping
    public ResponseEntity<ProjetoDTO> criar(@RequestBody ProjetoDTO dados) {
        return ResponseEntity.ok(projetoService.criar(dados));
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> atualizar(@PathVariable Long id, @RequestBody ProjetoDTO dados) {
        return ResponseEntity.ok(projetoService.atualizar(id, dados));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}