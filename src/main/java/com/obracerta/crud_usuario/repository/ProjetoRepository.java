package com.obracerta.crud_usuario.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.obracerta.crud_usuario.model.Projeto;
 
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByUsuarioId(Long usuarioId);
}