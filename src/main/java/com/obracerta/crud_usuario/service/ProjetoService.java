package com.obracerta.crud_usuario.service;

import com.obracerta.crud_usuario.dto.ProjetoDTO;
import com.obracerta.crud_usuario.model.Projeto;
import com.obracerta.crud_usuario.model.Tarefa; // Importante
import com.obracerta.crud_usuario.model.Usuario;
import com.obracerta.crud_usuario.repository.ProjetoRepository;
import com.obracerta.crud_usuario.repository.TarefaRepository; // Importante
import com.obracerta.crud_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- A CORREÇÃO ESTÁ AQUI ---
    @Autowired
    private TarefaRepository tarefaRepository; 
    // -----------------------------

    public List<ProjetoDTO> listarTodos() {
        return projetoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public ProjetoDTO criar(ProjetoDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setTitulo(dto.titulo());
        projeto.setDescricao(dto.descricao());
        projeto.setProgresso(dto.progresso() != null ? dto.progresso() : 0);

        if (dto.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.usuarioId()));
            projeto.setUsuario(usuario);
        }

        Projeto salvo = projetoRepository.save(projeto);
        return converterParaDTO(salvo);
    }

    public ProjetoDTO atualizar(Long id, ProjetoDTO dto) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com ID: " + id));

        projeto.setTitulo(dto.titulo());
        projeto.setDescricao(dto.descricao());
        // Se o front mandar progresso, usa. Se não, mantém o cálculo automático.
        if (dto.progresso() != null) {
             projeto.setProgresso(dto.progresso());
        }
        
        if (dto.usuarioId() != null) {
             Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            projeto.setUsuario(usuario);
        }

        Projeto atualizado = projetoRepository.save(projeto);
        return converterParaDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!projetoRepository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado para deleção.");
        }
        projetoRepository.deleteById(id);
    }

    // --- MÉTODO DE CÁLCULO ---
    public void recalcularProgresso(Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        // Agora essa linha vai funcionar porque declaramos a variável lá em cima!
        List<Tarefa> tarefas = tarefaRepository.findByProjetoId(projetoId);

        if (tarefas.isEmpty()) {
            projeto.setProgresso(0);
        } else {
            long totalTarefas = tarefas.size();
            
            // Verifique se no seu Model Tarefa o método é .getConcluida() ou .isConcluida()
            long concluidas = tarefas.stream()
                    .filter(tarefa -> Boolean.TRUE.equals(tarefa.getConcluida())) 
                    .count();

            int novaPorcentagem = (int) ((concluidas * 100) / totalTarefas);
            projeto.setProgresso(novaPorcentagem);
        }

        projetoRepository.save(projeto);
    }

    private ProjetoDTO converterParaDTO(Projeto projeto) {
        Long usuarioId = (projeto.getUsuario() != null) ? projeto.getUsuario().getId() : null;
        
        return new ProjetoDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getProgresso(),
                usuarioId
        );
    }
}