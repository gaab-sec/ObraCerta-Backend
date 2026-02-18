package com.obracerta.crud_usuario.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false) 
    @JsonIgnore 
    private Projeto projeto; 

    private String nome;

    @Column
    private int itensAFazer; 

    private int quantidadeFeita = 0; // Inicia em 0

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    public Tarefa() {
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getItensAFazer() { return itensAFazer; }
    public void setItensAFazer(int itensAFazer) { this.itensAFazer = itensAFazer; }
    public int getQuantidadeFeita() { return quantidadeFeita; }
    public void setQuantidadeFeita(int quantidadeFeita) { this.quantidadeFeita = quantidadeFeita; }
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }
}