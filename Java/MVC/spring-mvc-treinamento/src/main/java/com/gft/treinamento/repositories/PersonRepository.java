package com.gft.treinamento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.treinamento.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
