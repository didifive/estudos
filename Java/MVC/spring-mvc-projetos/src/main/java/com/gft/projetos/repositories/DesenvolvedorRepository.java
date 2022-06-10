package com.gft.projetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.projetos.entities.Desenvolvedor;

@Repository
public interface DesenvolvedorRepository extends JpaRepository<Desenvolvedor, Long>{
			
}
