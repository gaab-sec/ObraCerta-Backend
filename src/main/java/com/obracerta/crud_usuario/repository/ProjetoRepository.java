package com.obracerta.crud_usuario.repository;

import com.obracerta.crud_usuario.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    // Método extra para buscar projetos de um usuário específico
    // O Spring cria o SQL automaticamente só pelo nome do método!
    List<Projeto> findByUsuarioId(Long usuarioId);

}