package com.obracerta.crud_usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obracerta.crud_usuario.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}