package com.obracerta.tarefa.dto;

import com.obracerta.tarefa.model.Tarefa;

public class TarefaResponseDTO {
    private Long id;
    private String nome;
    private String itensAFazer;
    private int quantidadeFeita;
    private String prioridade;
    private Long projetoId;
    private int quantidadeTotal; 
    private double progressoPercentual;

    public TarefaResponseDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.itensAFazer = tarefa.getItensAFazer();
        this.quantidadeFeita = tarefa.getQuantidadeFeita();
        this.quantidadeTotal = tarefa.getQuantidadeTotal();
        
        if (tarefa.getQuantidadeTotal() > 0) {
            this.progressoPercentual = ((double) tarefa.getQuantidadeFeita() / tarefa.getQuantidadeTotal()) * 100.0;
        } else {
            this.progressoPercentual = 0.0;
        }

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
    public int getQuantidadeTotal() { return quantidadeTotal; }
    public double getProgressoPercentual() { return progressoPercentual; }
}