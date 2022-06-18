package com.gft.projetos.entities.builders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.utils.LinguagemUtils;
import com.gft.projetos.utils.ProjetoUtils;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Desenvolvedor")
class DesenvolvedorBuilderTest {
	
	private static final Long ID = 1L;
	private static final String NOME = "Bill Gates";
	private static final String QUATROLETRAS = "BIGA";
	private static final String EMAIL = "biga@gft.com";
	private static final BigDecimal SALARIOMENSAL = new BigDecimal("50000.00");
	private static final Linguagem LINGUAGEM = LinguagemUtils.createFakeEntity();
	private static final Projeto PROJETO = ProjetoUtils.createFakeEntity();
	private static final List<Projeto> PROJETOS = Collections.singletonList(PROJETO);
	
	@Test
	@DisplayName("1. Deve criar novo objeto Desenvolvedor com Id")
	void shouldCreateNewDesenvolvedorObjectWithId() throws Exception {
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withId(ID)
																.build();
		
		assertEquals(ID, desenvolvedor.getId());
		
	}
	
	@Test
	@DisplayName("2. Deve criar novo objeto Desenvolvedor com Nome")
	void shouldCreateNewDesenvolvedorObjectWithNome() throws Exception {
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withNome(NOME)
																.build();
		
		assertEquals(NOME, desenvolvedor.getNome());
		
	}

	@Test
	@DisplayName("3. Deve criar novo objeto Desenvolvedor com 4Letras")
	void shouldCreateNewDesenvolvedorObjectWithQuatroLetras() throws Exception {
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withQuatroLetras(QUATROLETRAS)
																.build();
		
		assertEquals(QUATROLETRAS, desenvolvedor.getQuatroLetras());
		
	}

	@Test
	@DisplayName("4. Deve criar novo objeto Desenvolvedor com E-mail")
	void shouldCreateNewDesenvolvedorObjectWithEmail() throws Exception {
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withEmail(EMAIL)
																.build();
		
		assertEquals(EMAIL, desenvolvedor.getEmail());
		
	}

	@Test
	@DisplayName("5. Deve criar novo objeto Desenvolvedor com Salario Mensal")
	void shouldCreateNewDesenvolvedorObjectWithSalarioMensal() throws Exception {
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withSalarioMensal(SALARIOMENSAL)
																.build();
		
		assertEquals(SALARIOMENSAL, desenvolvedor.getSalarioMensal());
		
	}

	@Test
	@DisplayName("6. Deve criar novo objeto Desenvolvedor com Linguagem")
	void shouldCreateNewDesenvolvedorObjectWithLinguagem() throws Exception {
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withLinguagem(LINGUAGEM)
																.build();
		
		assertEquals(LINGUAGEM, desenvolvedor.getLinguagem());
		
	}

	@Test
	@DisplayName("7. Deve criar novo objeto Desenvolvedor com Projetos")
	void shouldCreateNewDesenvolvedorObjectWithProjetos() throws Exception {
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withProjetos(PROJETOS)
																.build();
		
		assertEquals(PROJETOS, desenvolvedor.getProjetos());
		
	}
	
	@Test
	@DisplayName("8. Deve criar novo objeto Desenvolvedor com adição de objeto Projeto em Projetos")
	void shouldCreateNewDesenvolvedorObjectWithProjetoObjectInProjetos() throws Exception {
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withProjeto(PROJETO)
																.build();
		
		assertThat(desenvolvedor.getProjetos()).contains(PROJETO);
		
	}
	
	@Test
	@DisplayName("8.1. Deve criar novo objeto Desenvolvedor com adição de objeto Projeto em Projetos que já tinha Projeto")
	void shouldCreateNewDesenvolvedorObjectWithProjetoObjectInProjetosWhoAlreadyHadProjeto() throws Exception {
		Projeto projeto = ProjetoUtils.createFakeEntity();
		
		Desenvolvedor desenvolvedor = new DesenvolvedorBuilder()
																.withProjeto(PROJETO)
																.withProjeto(projeto)
																.build();
		
		assertAll(
			"Verifica o tamanho da lista de projetos e se existe o objeto projeto"
			,() -> assertEquals(2, desenvolvedor.getProjetos().size())
			,() -> assertThat(desenvolvedor.getProjetos()).contains(projeto)
		);
		
		
		
	}

}
