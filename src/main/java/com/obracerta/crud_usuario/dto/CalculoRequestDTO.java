package com.obracerta.crud_usuario.dto;

public record CalculoRequestDTO(
    Long projetoId,
    String superficie,
    String material,
    Double altura,
    Double largura
) {}