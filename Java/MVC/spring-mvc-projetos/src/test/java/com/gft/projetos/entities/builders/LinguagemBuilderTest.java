package com.gft.projetos.entities.builders;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Linguagem")
class LinguagemBuilderTest {
	
	@Mock
	private static Desenvolvedor desenvolvedor;
	
	@Mock
	private static Projeto projeto;
	
	private static final Long ID = 1L;
	private static final String NOME = "Java";
	private static final List<Desenvolvedor> DESENVOLVEDORES = new ArrayList<>(Arrays.asList(desenvolvedor));
	private static final List<Projeto> PROJETOS = new ArrayList<>(Arrays.asList(projeto));
	
	@Test
	@DisplayName("1. Deve criar novo objeto Linguagem com Id")
	void shouldCreateNewLinguagemObjectWithId() throws Exception {
		
		Linguagem linguagem = new LinguagemBuilder()
																	.withId(ID)
																	.build();
		
		assertEquals(ID, linguagem.getId());
		
	}
	
	@Test
	@DisplayName("2. Deve criar novo objeto Linguagem com Nome")
	void shouldCreateNewLinguagemObjectWithNome() throws Exception {
		
		Linguagem linguagem = new LinguagemBuilder()
																	.withNome(NOME)
																	.build();
		
		assertEquals(NOME, linguagem.getNome());
		
	}
	
	@Test
	@DisplayName("3. Deve criar novo objeto Linguagem com Desenvolvedores")
	void shouldCreateNewLinguagemObjectWithDesenvolvedores() throws Exception {
		
		Linguagem linguagem = new LinguagemBuilder()
																	.withDesenvolvedores(DESENVOLVEDORES)
																	.build();
		
		assertEquals(DESENVOLVEDORES, linguagem.getDesenvolvedores());
		
	}
	
	@Test
	@DisplayName("4. Deve criar novo objeto Linguagem com Projetos")
	void shouldCreateNewLinguagemObjectWithProjetos() throws Exception {
		
		Linguagem linguagem = new LinguagemBuilder()
																	.withProjetos(PROJETOS)
																	.build();
		
		assertEquals(PROJETOS, linguagem.getProjetos());
		
	}

}
