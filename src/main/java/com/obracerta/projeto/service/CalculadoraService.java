package com.obracerta.projeto.service;

import com.obracerta.projeto.dto.CalculoRequestDTO;
import com.obracerta.projeto.dto.CalculoResponseDTO;
import com.obracerta.projeto.model.Calculo;
import com.obracerta.projeto.model.Projeto;
import com.obracerta.projeto.repository.CalculoRepository;
import com.obracerta.projeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private CalculoRepository calculoRepository;

    public CalculoResponseDTO realizarCalculo(CalculoRequestDTO dados) {
        // 1. Validação
        Projeto projeto = projetoRepository.findById(dados.projetoId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));

        // 2. Cálculo Base da Área
        double area = dados.altura() * dados.largura();

        // 3. Inteligência do Material
        ResultadoMaterial resultado = calcularConsumoDetalhado(dados.superficie(), dados.material(), area);

        // 4. Persistência
        Calculo novoCalculo = new Calculo(
                dados.material(),
                area,
                resultado.quantidade,
                resultado.unidade,
                projeto
        );
        calculoRepository.save(novoCalculo);

        return new CalculoResponseDTO(
                area,
                resultado.quantidade,
                resultado.unidade,
                resultado.observacao // Adicionei uma msg explicativa no DTO
        );
    }

    private ResultadoMaterial calcularConsumoDetalhado(String superficie, String materialNome, double area) {
        String material = materialNome.toLowerCase().trim();
        String tipoSuperficie = superficie.toLowerCase().trim();

        double quantidade = 0.0;
        String unidade = "unidades";
        String obs = "";

        // --- LÓGICA DE PAREDES (Blocos e Tijolos) ---
        if (tipoSuperficie.equals("parede")) {
            if (material.contains("bloco cerâmico") || material.contains("baiano")) {
                // Padrão 9x19x19 (aprox 25 p/ m²)
                quantidade = Math.ceil(area * 25);
                unidade = "blocos";
                obs = "Considerando bloco cerâmico padrão 9x19x19";
            } else if (material.contains("concreto")) {
                // Padrão 14x19x39 (aprox 12.5 p/ m²)
                quantidade = Math.ceil(area * 12.5);
                unidade = "blocos";
                obs = "Considerando bloco de concreto estrutural 14x19x39";
            } else if (material.contains("tijolinho") || material.contains("ecológico")) {
                // Tijolinho comum maciço (aprox 80 p/ m² deitada)
                quantidade = Math.ceil(area * 80);
                unidade = "tijolos";
                obs = "Estimativa para assentamento deitado";
            } else if (material.contains("drywall")) {
                // Chapa padrão 1.20m x 2.40m = 2.88m²
                quantidade = Math.ceil(area / 2.88);
                unidade = "chapas (1.20x2.40m)";
                obs = "Arredondado para cima (chapas inteiras)";
            }
        }

        // --- LÓGICA DE PISOS E REVESTIMENTOS (Com 10% de perda) ---
        else if (tipoSuperficie.equals("piso") || material.contains("porcelanato") || material.contains("cerâmica")) {
            // Regra geral de pisos: Área + 10% de perda (recortes/rodapé)
            quantidade = area * 1.10;
            // Arredondar para 2 casas decimais
            quantidade = Math.round(quantidade * 100.0) / 100.0;
            unidade = "m²";
            obs = "Incluso 10% de margem de segurança para recortes";
        }

        // --- LÓGICA DE TEXTURAS E REBOCOS ---
        else if (tipoSuperficie.equals("revestimento") || tipoSuperficie.equals("reboco")) {
            if (material.contains("textura")) {
                // Consumo médio: 1.5kg a 2.5kg por m². Vamos usar média 2kg.
                quantidade = area * 2.0;
                unidade = "kg";
                obs = "Estimativa média de 2kg/m²";
            } else {
                // Reboco tradicional (Massa): Calculamos em m² ou m³
                // Vamos manter m² para facilitar compra de serviço, ou calcular volume (espessura 2cm)
                quantidade = area * 0.02; // 2cm de espessura
                unidade = "m³ (volume de argamassa)";
                obs = "Considerando espessura média de 2cm";
            }
        }

        // --- LÓGICA DE CONTRAPISO E LAJE ---
        else if (tipoSuperficie.equals("contrapiso") || tipoSuperficie.equals("laje")) {
             // Contrapiso geralmente 5cm (0.05m)
             // Laje pré-moldada é comprada por m², mas concreto é volume.
             if (material.contains("concreto") || material.contains("cimento")) {
                 double espessura = tipoSuperficie.equals("laje") ? 0.10 : 0.05; // 10cm laje, 5cm contrapiso
                 quantidade = area * espessura;
                 unidade = "m³ de concreto";
                 obs = "Considerando espessura de " + (espessura*100) + "cm";
             } else {
                 // Lajotas, Pré-moldada, Isopor
                 quantidade = area; // Compra-se por m²
                 unidade = "m²";
                 obs = "Área exata da superfície";
             }
        }
        
        // --- LÓGICA DE FORRO (Gesso) ---
        else if (tipoSuperficie.equals("forro")) {
            if(material.contains("gesso")) {
                 // Placa padrão 60x60cm = 0.36m²
                 quantidade = Math.ceil(area / 0.36);
                 unidade = "placas (60x60)";
                 obs = "Para forro de plaquinha 60x60";
            } else {
                quantidade = area;
                unidade = "m²";
                obs = "";
            }
        }

        // --- DEFAULT (Caso não caia em nenhuma regra específica) ---
        else {
            quantidade = area;
            unidade = "m²";
            obs = "Cálculo base de área";
        }

        return new ResultadoMaterial(quantidade, unidade, obs);
    }

    // Record interno auxiliar atualizado com observação
    private record ResultadoMaterial(double quantidade, String unidade, String observacao) {}
}