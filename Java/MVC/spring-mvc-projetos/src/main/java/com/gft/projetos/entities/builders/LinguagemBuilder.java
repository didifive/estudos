package com.gft.projetos.entities.builders;

import java.util.List;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;

public class LinguagemBuilder {

	private Linguagem instancia;
	
	public LinguagemBuilder() {
		instancia = new Linguagem();
	}
	
	public Linguagem build() {
		return instancia;
	}
	
	public LinguagemBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public LinguagemBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}

	public LinguagemBuilder withDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
		instancia.setDesenvolvedores(desenvolvedores);
		return this;
	}
	
	public LinguagemBuilder withProjetos(List<Projeto> projetos) {
		instancia.setProjetos(projetos);
		return this;
	}
}
