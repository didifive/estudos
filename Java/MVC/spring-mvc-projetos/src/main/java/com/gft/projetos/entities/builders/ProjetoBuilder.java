package com.gft.projetos.entities.builders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;

public class ProjetoBuilder {
	
	private Projeto instancia;
	
	public ProjetoBuilder() {
		instancia = new Projeto();
	}
	
	public Projeto build() {
		return instancia;
	}
	
	public ProjetoBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public ProjetoBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}

	public ProjetoBuilder withApelido(String apelido) {
		instancia.setApelido(apelido);
		return this;
	}
	
	public ProjetoBuilder withDataEntrega(Date dataEntrega) {
		instancia.setDataEntrega(dataEntrega);
		return this;
	}
	
	public ProjetoBuilder withOrcamento(BigDecimal orcamento) {
		instancia.setOrcamento(orcamento);
		return this;
	}
	
	public ProjetoBuilder withLinguagem(Linguagem linguagem) {
		instancia.setLinguagem(linguagem);
		return this;
	}

	public ProjetoBuilder withDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
		instancia.setDesenvolvedores(desenvolvedores);
		return this;
	}

}
