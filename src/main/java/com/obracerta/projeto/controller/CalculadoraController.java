package com.obracerta.projeto.controller;

import com.obracerta.projeto.dto.CalculoRequestDTO;
import com.obracerta.projeto.dto.CalculoResponseDTO;
import com.obracerta.projeto.service.CalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculadora")
@CrossOrigin(origins = "*")
public class CalculadoraController {

    @Autowired
    private CalculadoraService calculadoraService;

    @PostMapping
    public ResponseEntity<CalculoResponseDTO> calcular(@RequestBody CalculoRequestDTO request) {
        CalculoResponseDTO resultado = calculadoraService.realizarCalculo(request);
        return ResponseEntity.ok(resultado);
    }
}