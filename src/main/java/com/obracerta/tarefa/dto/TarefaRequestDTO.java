package com.obracerta.tarefa.dto;

public class TarefaRequestDTO {
    private String nome;
    private String itensAFazer;
    private String prioridade;
    private Long projetoId;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getItensAFazer() { return itensAFazer; }
    public void setItensAFazer(String itensAFazer) { this.itensAFazer = itensAFazer; }
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public Long getProjetoId() { return projetoId; }
    public void setProjetoId(Long projetoId) { this.projetoId = projetoId; }
}