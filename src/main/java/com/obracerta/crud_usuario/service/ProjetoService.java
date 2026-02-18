package com.obracerta.crud_usuario.service;

import com.obracerta.crud_usuario.model.Projeto;
import com.obracerta.crud_usuario.model.Tarefa;
import com.obracerta.crud_usuario.repository.ProjetoRepository;
import com.obracerta.crud_usuario.repository.TarefaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

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

    public void recalcularProgresso(Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado com id: " + projetoId));

        // Busca todas as tarefas associadas a este projeto
        List<Tarefa> tarefas = tarefaRepository.findByProjetoId(projetoId);

        // Calcula a soma total da 'quantidadeFeita'
        int progressoCalculado = tarefas.stream()
            .mapToInt(Tarefa::getQuantidadeFeita)
            .sum();

        // Atualiza o campo 'progresso'
        projeto.setProgresso(progressoCalculado);
        projetoRepository.save(projeto);
    }
}