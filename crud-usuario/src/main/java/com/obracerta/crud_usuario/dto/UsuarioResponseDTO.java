package com.obracerta.crud_usuario.dto;

import com.obracerta.crud_usuario.model.Usuario;

// Este DTO será usado para *retornar* os dados do usuário para o cliente
// Note que ele NÃO tem o campo 'senha'
public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email
) {
    // Este é um construtor "extra" que facilita a conversão
    // da nossa Entidade 'Usuario' para este DTO
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}