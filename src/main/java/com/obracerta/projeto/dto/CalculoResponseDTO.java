package com.obracerta.projeto.dto;

public record CalculoResponseDTO(
    Double areaTotal,
    Double quantidadeNecessaria,
    String unidadeMedida,
    String mensagem // ou observacao
) {}