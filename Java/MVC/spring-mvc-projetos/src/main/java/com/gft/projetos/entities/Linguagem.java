package com.gft.projetos.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Linguagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	private String nome;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "linguagem", cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private List<Desenvolvedor> desenvolvedores;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "linguagem", cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private List<Projeto> projetos;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Desenvolvedor> getDesenvolvedores() {
		return desenvolvedores;
	}
	public void setDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
		this.desenvolvedores = desenvolvedores;
	}
	public List<Projeto> getProjetos() {
		return projetos;
	}
	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(desenvolvedores, id, nome, projetos);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linguagem other = (Linguagem) obj;
		return Objects.equals(desenvolvedores, other.desenvolvedores) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(projetos, other.projetos);
	}
	
	
	
}