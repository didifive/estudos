package com.gft.projetos.entities.builders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;

public class DesenvolvedorBuilder {

	private Desenvolvedor instancia;
	
	public DesenvolvedorBuilder() {
		instancia = new Desenvolvedor();
	}
	
	public Desenvolvedor build() {
		return instancia;
	}
	
	public DesenvolvedorBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public DesenvolvedorBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}

	public DesenvolvedorBuilder withQuatroLetras(String quatroLetras) {
		instancia.setQuatroLetras(quatroLetras);
		return this;
	}
	
	public DesenvolvedorBuilder withEmail(String email) {
		instancia.setEmail(email);
		return this;
	}
	
	public DesenvolvedorBuilder withSalarioMensal(BigDecimal salarioMensal) {
		instancia.setSalarioMensal(salarioMensal);
		return this;
	}
	
	public DesenvolvedorBuilder withLinguagem(Linguagem linguagem) {
		instancia.setLinguagem(linguagem);
		return this;
	}

	public DesenvolvedorBuilder withProjetos(List<Projeto> projetos) {
		instancia.setProjetos(projetos);
		return this;
	}
	
	public DesenvolvedorBuilder withProjeto(Projeto projeto) {
		List<Projeto> projetos = new ArrayList<>();
		if(instancia.getProjetos() != null) {
			projetos = instancia.getProjetos().stream().collect(Collectors.toList());
		}
		projetos.add(projeto);
		instancia.setProjetos(projetos);
		return this;
	}
	
}
