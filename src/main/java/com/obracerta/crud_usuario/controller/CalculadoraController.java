package com.obracerta.crud_usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obracerta.crud_usuario.dto.CalculoRequestDTO;
import com.obracerta.crud_usuario.dto.CalculoResponseDTO;
import com.obracerta.crud_usuario.service.CalculadoraService;

@RestController
@RequestMapping("/api/calculadora")
// @CrossOrigin("*") // Não precisa se já configurou no SecurityConfig
public class CalculadoraController {

    @Autowired
    private CalculadoraService calculadoraService;

    @PostMapping
    public ResponseEntity<CalculoResponseDTO> realizarCalculo(@RequestBody CalculoRequestDTO dados) {
        
        // Chama o serviço para fazer a conta
        CalculoResponseDTO resultado = calculadoraService.calcular(dados);
        
        return ResponseEntity.ok(resultado);
    }
}