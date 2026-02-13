package com.obracerta.crud_usuario.dto;

public record CalculoResponseDTO(
    String mensagem,
    Double quantidadeNecessaria,
    String unidadeMedida,
    Double areaTotal
) {}