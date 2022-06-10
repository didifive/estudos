package com.gft.projetos.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotEmpty(message = "Apelido não pode ser vazio")
	@Size(max = 8, message="Apelido deve conter no máximo 8 caracteres")
	private String apelido;
	
	@JsonBackReference
	@ManyToOne
	private Linguagem linguagem;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataEntrega;
	
	private BigDecimal orcamento;
	
	@JsonBackReference
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
      name = "desenvolvedores_projetos",
      joinColumns = {
          @JoinColumn(name = "desenvolvedor_id")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "projeto_id")
      }
  )
	private List<Desenvolvedor> desenvolvedores;

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

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public Linguagem getLinguagem() {
		return linguagem;
	}

	public void setLinguagem(Linguagem linguagem) {
		this.linguagem = linguagem;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public BigDecimal getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(BigDecimal orcamento) {
		this.orcamento = orcamento;
	}

	public List<Desenvolvedor> getDesenvolvedores() {
		return desenvolvedores;
	}

	public void setDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
		this.desenvolvedores = desenvolvedores;
	}
	
	
	
}
