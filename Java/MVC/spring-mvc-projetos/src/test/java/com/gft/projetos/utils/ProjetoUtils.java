package com.gft.projetos.utils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.mockito.Mock;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.entities.builders.ProjetoBuilder;

public class ProjetoUtils {
	
	@Mock
	private static Linguagem linguagem;
	
	@Mock
	private static Desenvolvedor desenvolvedor;
	
	private static final Long ID = 1L;
	private static final String NOME = "Windows 95";
	private static final String APELIDO = "W95";
	private static final Date DATAENTREGA = Date.from(Instant.now());
	private static final BigDecimal ORCAMENTO = new BigDecimal("50000.00");
	private static final Linguagem LINGUAGEM = linguagem;
	private static final List<Desenvolvedor> DESENVOLVEDORES = Collections.singletonList(desenvolvedor);

  public static Projeto createFakeEntity() {
      return new ProjetoBuilder()
                    .withId(ID)
                    .withNome(NOME)
                    .withApelido(APELIDO)
                    .withDataEntrega(DATAENTREGA)
                    .withOrcamento(ORCAMENTO)
                    .withLinguagem(LINGUAGEM)
                    .withDesenvolvedores(DESENVOLVEDORES)
                    .build();
  }
  
}