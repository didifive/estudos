package com.gft.projetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.projetos.entities.Linguagem;

@Repository
public interface LinguagemRepository extends JpaRepository<Linguagem, Long>{

}
