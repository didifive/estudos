package com.gft.projetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.projetos.entities.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long>{
	
	List<Projeto> findByNomeContainsAndApelidoContains(String nome, String apelido);

}
