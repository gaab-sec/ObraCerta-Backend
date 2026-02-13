package com.obracerta.crud_usuario.service;

import org.springframework.stereotype.Service;

import com.obracerta.crud_usuario.dto.CalculoRequestDTO;
import com.obracerta.crud_usuario.dto.CalculoResponseDTO;

@Service
public class CalculadoraService {

    public CalculoResponseDTO calcular(CalculoRequestDTO dados) {
        // 1. Calcula a Área Base (Largura x Altura)
        double area = dados.largura() * dados.altura();
        double quantidade = 0.0;
        String unidade = "unidades";
        String msg = "Cálculo realizado com sucesso!";

        // 2. Lógica simples baseada na Superfície (Pode expandir depois)
        switch (dados.superficie().toLowerCase()) {
            case "piso":
            case "contrapiso":
            case "laje":
                // Exemplo: Piso geralmente vende por m² + 10% de perda (recortes)
                quantidade = area * 1.10; 
                unidade = "m² (com 10% de margem)";
                break;

            case "parede":
            case "reboco":
            case "revestimento":
                // Exemplo: Parede também é m²
                quantidade = area; 
                unidade = "m²";
                break;
                
            case "forro":
                quantidade = area;
                unidade = "m² de forro";
                break;

            default:
                // Se não conhecer, devolve só a área
                quantidade = area;
                unidade = "m²";
                msg = "Material não específico identificado, retornando área total.";
                break;
        }

        // 3. Arredonda para 2 casas decimais
        quantidade = Math.round(quantidade * 100.0) / 100.0;
        area = Math.round(area * 100.0) / 100.0;

        return new CalculoResponseDTO(msg, quantidade, unidade, area);
    }
}