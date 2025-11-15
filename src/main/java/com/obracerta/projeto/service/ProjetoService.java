package com.obracerta.projeto.service;

import com.obracerta.projeto.model.Projeto;
import com.obracerta.projeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    // Criar
    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    // Listar todos
    public List<Projeto> listarTodosProjetos() {
        return projetoRepository.findAll();
    }

    // Buscar por ID
    public Optional<Projeto> buscarProjetoPorId(Long id) {
        return projetoRepository.findById(id);
    }

    // Atualizar
    public Projeto atualizarProjeto(Long id, Projeto detalhesProjeto) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com id: " + id));

        projeto.setTitulo(detalhesProjeto.getTitulo());
        projeto.setDescricao(detalhesProjeto.getDescricao());
        projeto.setProgresso(detalhesProjeto.getProgresso());

        return projetoRepository.save(projeto);
    }

    // Deletar
    public void deletarProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com id: " + id));
        projetoRepository.delete(projeto);
    }
}