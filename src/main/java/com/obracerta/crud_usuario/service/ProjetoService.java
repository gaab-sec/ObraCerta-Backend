package com.obracerta.crud_usuario.service;
 
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.obracerta.crud_usuario.dto.ProjetoDTO;
import com.obracerta.crud_usuario.model.Projeto;
import com.obracerta.crud_usuario.model.Tarefa;
import com.obracerta.crud_usuario.model.Usuario;
import com.obracerta.crud_usuario.repository.ProjetoRepository;
import com.obracerta.crud_usuario.repository.TarefaRepository;
import com.obracerta.crud_usuario.repository.UsuarioRepository;
 
@Service
public class ProjetoService {
 
    @Autowired
    private ProjetoRepository projetoRepository;
 
    @Autowired
    private UsuarioRepository usuarioRepository;
 
    @Autowired
    private TarefaRepository tarefaRepository;
 
    public List<ProjetoDTO> listarPorUsuario(Long usuarioId) {
        return projetoRepository.findByUsuarioId(usuarioId).stream()
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
 
        return converterParaDTO(projetoRepository.save(projeto));
    }
 
    public ProjetoDTO atualizar(Long id, ProjetoDTO dto) {
        Projeto projeto = projetoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado com ID: " + id));
 
        projeto.setTitulo(dto.titulo());
        projeto.setDescricao(dto.descricao());
 
        if (dto.progresso() != null) {
            projeto.setProgresso(dto.progresso());
        }
        if (dto.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            projeto.setUsuario(usuario);
        }
 
        return converterParaDTO(projetoRepository.save(projeto));
    }
 
    public void deletar(Long id) {
        if (!projetoRepository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado para deleção.");
        }
        projetoRepository.deleteById(id);
    }
 
    public void recalcularProgresso(Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
 
        List<Tarefa> tarefas = tarefaRepository.findByProjetoId(projetoId);
 
        if (tarefas.isEmpty()) {
            projeto.setProgresso(0);
        } else {
            long total = tarefas.size();
            long concluidas = tarefas.stream()
                .filter(t -> Boolean.TRUE.equals(t.getConcluida()))
                .count();
            projeto.setProgresso((int) ((concluidas * 100) / total));
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

    public Object listarTodos() {
        throw new UnsupportedOperationException("Unimplemented method 'listarTodos'");
    }
}