package com.obracerta.crud_usuario.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Indica que esta classe é uma entidade JPA
@Table(name = "usuarios") // Define o nome da tabela
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;

    @Column(unique = true)
    private String email;

    // IMPORTANTE: No Postgres, garanta que essa coluna tenha tamanho suficiente
    // Se não definir length, o padrão é 255 (ok). Se definiu, tem que ser > 60.
    private String senha; // Esta será a senha HASHED (criptografada)

    // Construtores, Getters e Setters (Obrigatório)
    // (A sua IDE pode gerar isso facilmente)
    
    public Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
        // Como você não tem perfis (ADMIN, USER), retorna lista vazia
        return List.of(); 
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; // O Spring usa "username", mas devolvemos o email
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
    
}