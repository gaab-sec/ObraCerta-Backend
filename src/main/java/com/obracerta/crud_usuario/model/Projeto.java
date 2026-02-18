package com.obracerta.crud_usuario.model;

import jakarta.persistence.*; // Se der erro aqui, tente 'javax.persistence.*'

@Entity
@Table(name = "projetos") // Nome da tabela no banco
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000) // Permite descrições longas
    private String descricao;

    private Integer progresso = 0; // Começa com 0%

    // RELACIONAMENTO: Muitos Projetos pertencem a Um Usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // --- CONSTRUTORES ---
    public Projeto() {
    }

    public Projeto(String titulo, String descricao, Integer progresso, Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.progresso = progresso;
        this.usuario = usuario;
    }

    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getProgresso() {
        return progresso;
    }

    public void setProgresso(Integer progresso) {
        this.progresso = progresso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}