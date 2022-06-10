package com.gft.projetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.projetos.entities.Desenvolvedor;

@Repository
public interface DesenvolvedorRepository extends JpaRepository<Desenvolvedor, Long>{
	
	List<Desenvolvedor> findByNomeContainsAndQuatroLetrasContains(String nome, String quatroLetras);
			
}
