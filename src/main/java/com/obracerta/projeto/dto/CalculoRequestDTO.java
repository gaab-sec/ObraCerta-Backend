package com.obracerta.projeto.dto;

public record CalculoRequestDTO(
    Long projetoId,      // O ID do projeto onde vamos salvar o cálculo
    String superficie,   // ex: "piso", "parede"
    String material,     // ex: "Porcelanato", "Tijolo"
    Double altura,
    Double largura
) {}