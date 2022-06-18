package com.gft.projetos.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Desenvolvedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotEmpty(message = "4Letras não pode ser vazio")
	@Size(min = 4,max = 4, message="4letras deve conter 4 caracteres")
	private String quatroLetras;
	
	@NotEmpty(message = "Email não pode ser vazio")
	@Email
	private String email;
	
	@Digits(fraction = 2, integer = 10)
	private BigDecimal salarioMensal;
	
	@JsonBackReference
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private Linguagem linguagem;
	
	@JsonManagedReference
	@ManyToMany(mappedBy = "desenvolvedores", cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
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

	public String getQuatroLetras() {
		return quatroLetras;
	}

	public void setQuatroLetras(String quatroLetras) {
		this.quatroLetras = quatroLetras;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getSalarioMensal() {
		return salarioMensal;
	}

	public void setSalarioMensal(BigDecimal salarioMensal) {
		this.salarioMensal = salarioMensal;
	}

	public Linguagem getLinguagem() {
		return linguagem;
	}

	public void setLinguagem(Linguagem linguagem) {
		this.linguagem = linguagem;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, linguagem, nome, projetos, quatroLetras, salarioMensal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Desenvolvedor other = (Desenvolvedor) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(linguagem, other.linguagem) && Objects.equals(nome, other.nome)
				&& Objects.equals(projetos, other.projetos) && Objects.equals(quatroLetras, other.quatroLetras)
				&& Objects.equals(salarioMensal, other.salarioMensal);
	}	

}
