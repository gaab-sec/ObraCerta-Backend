package com.obracerta.projeto.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importante para evitar loop infinito no JSON

@Entity
@Table(name = "calculos")
public class Calculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String material;
    private Double area;
    private Double quantidadeResultante;
    private String unidade;

    // VINCULO COM O PROJETO EXISTENTE
    @ManyToOne
    @JoinColumn(name = "projeto_id")
    @JsonIgnore // Oculta o projeto inteiro quando listamos o calculo
    private Projeto projeto;

    public Calculo() {}

    // Construtor utilitário
    public Calculo(String material, Double area, Double quantidadeResultante, String unidade, Projeto projeto) {
        this.material = material;
        this.area = area;
        this.quantidadeResultante = quantidadeResultante;
        this.unidade = unidade;
        this.projeto = projeto;
    }

    // Getters e Setters...
    public Long getId() { return id; }
    public String getMaterial() { return material; }
    public Double getQuantidadeResultante() { return quantidadeResultante; }
    // ... gere os outros getters/setters aqui

    public void setId(Long id) {
        this.id = id;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setQuantidadeResultante(Double quantidadeResultante) {
        this.quantidadeResultante = quantidadeResultante;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    
}