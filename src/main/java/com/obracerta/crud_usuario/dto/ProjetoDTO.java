package com.obracerta.crud_usuario.dto;

public record ProjetoDTO(
    Long id,
    String titulo,
    String descricao,
    Integer progresso,
    Long usuarioId // Para saber de quem é o projeto
) {}