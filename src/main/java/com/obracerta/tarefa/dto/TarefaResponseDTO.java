package com.obracerta.tarefa.dto;

import com.obracerta.tarefa.model.Tarefa;

public class TarefaResponseDTO {
    private Long id;
    private String nome;
    private String itensAFazer;
    private int quantidadeFeita;
    private String prioridade;
    private Long projetoId;

    public TarefaResponseDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.itensAFazer = tarefa.getItensAFazer();
        this.quantidadeFeita = tarefa.getQuantidadeFeita();
        this.prioridade = tarefa.getPrioridade().name();
        this.projetoId = tarefa.getProjeto().getId();
    }

    // Getters e Setters (omitidos para brevidade, mas devem existir)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getItensAFazer() { return itensAFazer; }
    public int getQuantidadeFeita() { return quantidadeFeita; }
    public String getPrioridade() { return prioridade; }
    public Long getProjetoId() { return projetoId; }
}