package com.obracerta.projeto.repository;

import com.obracerta.projeto.model.Calculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculoRepository extends JpaRepository<Calculo, Long> {

    // MAGIA DO SPRING DATA:
    // Apenas declarando este método, o Spring cria automaticamente o SQL 
    // para buscar todos os cálculos onde a coluna projeto_id for igual ao parâmetro.
    List<Calculo> findByProjetoId(Long projetoId);
    
}