package com.obracerta.tarefa.dto;

import com.obracerta.tarefa.model.Tarefa;

public class TarefaResponseDTO {
    private Long id;
    private String nome;
    private int itensAFazer;
    private int quantidadeFeita;
    private double porcentagemConclusao;
    private String prioridade;
    private Long projetoId;

    public TarefaResponseDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.itensAFazer = tarefa.getItensAFazer();
        this.quantidadeFeita = tarefa.getQuantidadeFeita();
        this.prioridade = tarefa.getPrioridade().name();
        this.projetoId = tarefa.getProjeto().getId();
        this.porcentagemConclusao = calcularPorcentagem(tarefa.getQuantidadeFeita(), tarefa.getItensAFazer());
    }

    private double calcularPorcentagem(int feita, int total) {
        if (total<=0) {
            return 0.0;
        }
        return Math.round(((double) feita / total) * 10000.0) / 100.0;
    } 

    // Getters e Setters (omitidos para brevidade, mas devem existir)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getItensAFazer() { return itensAFazer; }
    public int getQuantidadeFeita() { return quantidadeFeita; }
    public double getPorcentagemConclusao() {return porcentagemConclusao;}
    public String getPrioridade() { return prioridade; }
    public Long getProjetoId() { return projetoId; }
}