package com.gft.projetos.entities.builders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.utils.DesenvolvedorUtils;
import com.gft.projetos.utils.LinguagemUtils;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Projeto")
class ProjetoBuilderTest {
	
	private static final Long ID = 1L;
	private static final String NOME = "Windows 95";
	private static final String APELIDO = "W95";
	private static final Date DATAENTREGA = Date.from(Instant.now());
	private static final BigDecimal ORCAMENTO = new BigDecimal("50000.00");
	private static final Linguagem LINGUAGEM = LinguagemUtils.createFakeEntity();
	private static final Desenvolvedor DESENVOLVEDOR = DesenvolvedorUtils.createFakeEntity();
	private static final List<Desenvolvedor> DESENVOLVEDORES = Collections.singletonList(DESENVOLVEDOR);
	
	@Test
	@DisplayName("1. Deve criar novo objeto Projeto com Id")
	void shouldCreateNewProjetoObjectWithId() throws Exception {
		
		Projeto projeto = new ProjetoBuilder()
																.withId(ID)
																.build();
		
		assertEquals(ID, projeto.getId());
		
	}
	
	@Test
	@DisplayName("2. Deve criar novo objeto Projeto com Nome")
	void shouldCreateNewProjetoObjectWithNome() throws Exception {
		
		Projeto projeto = new ProjetoBuilder()
																.withNome(NOME)
																.build();
		
		assertEquals(NOME, projeto.getNome());
		
	}
	
	@Test
	@DisplayName("3. Deve criar novo objeto Projeto com Apelido")
	void shouldCreateNewProjetoObjectWithApelido() throws Exception {
		
		Projeto projeto = new ProjetoBuilder()
																.withApelido(APELIDO)
																.build();
		
		assertEquals(APELIDO, projeto.getApelido());
		
	}
	
	@Test
	@DisplayName("4. Deve criar novo objeto Projeto com Data de Entrega")
	void shouldCreateNewProjetoObjectWithIdDataEntrega() throws Exception {
		
		Projeto projeto = new ProjetoBuilder()
																.withDataEntrega(DATAENTREGA)
																.build();
		
		assertEquals(DATAENTREGA, projeto.getDataEntrega());
		
	}
	
	@Test
	@DisplayName("5. Deve criar novo objeto Projeto com Orcamento")
	void shouldCreateNewProjetoObjectWithOrcamento() throws Exception {
		
		Projeto projeto = new ProjetoBuilder()
																.withOrcamento(ORCAMENTO)
																.build();
		
		assertEquals(ORCAMENTO, projeto.getOrcamento());
		
	}
	
	@Test
	@DisplayName("6. Deve criar novo objeto Projeto com Linguagem")
	void shouldCreateNewProjetoObjectWithIdLinguagem() throws Exception {
		
		Projeto projeto = new ProjetoBuilder()
																.withLinguagem(LINGUAGEM)
																.build();
		
		assertEquals(LINGUAGEM, projeto.getLinguagem());
		
	}
	
	@Test
	@DisplayName("7. Deve criar novo objeto Projeto com Desenvolvedores")
	void shouldCreateNewProjetoObjectWithDesenvolvedores() throws Exception {
		
		Projeto projeto = new ProjetoBuilder()
																.withDesenvolvedores(DESENVOLVEDORES)
																.build();
		
		assertEquals(DESENVOLVEDORES, projeto.getDesenvolvedores());
		
	}
	
	@Test
	@DisplayName("8. Deve criar novo objeto Projeto com adição de objeto Desenvolvedor em Desenvolvedores")
	void shouldCreateNewProjetoObjectWithDesenvolvedorObjectInDesenvolvedores() throws Exception {
		Projeto projeto = new ProjetoBuilder()
																.withDesenvolvedor(DESENVOLVEDOR)
																.build();
		
		assertThat(projeto.getDesenvolvedores()).contains(DESENVOLVEDOR);
		
	}
	
	@Test
	@DisplayName("8. Deve criar novo objeto Projeto com adição de objeto Desenvolvedor em Desenvolvedores que já tinha Desenvolvedor")
	void shouldCreateNewProjetoObjectWithDesenvolvedorObjectInDesenvolvedoresWhoAlreadyHadDesenvolvedor() throws Exception {
		Desenvolvedor desenvolvedor = DesenvolvedorUtils.createFakeEntity();
		
		Projeto projeto = new ProjetoBuilder()
																.withDesenvolvedor(DESENVOLVEDOR)
																.withDesenvolvedor(desenvolvedor)
																.build();
		
		assertAll(
			"Verifica o tamanho da lista de desenvolvedores e se existe o objeto desenvolvedor"
			,() -> assertEquals(2, projeto.getDesenvolvedores().size())
			,() -> assertThat(projeto.getDesenvolvedores()).contains(desenvolvedor)
		);
		
		
	}

}
