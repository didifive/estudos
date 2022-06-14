package com.gft.projetos.utils;

import java.util.Collections;
import java.util.List;

import org.mockito.Mock;
import org.mockito.Spy;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.entities.builders.LinguagemBuilder;


public class LinguagemUtils {
	
	@Spy
	private static Desenvolvedor desenvolvedor;
	
	@Spy
	private static Projeto projeto;
	
	private static final Long ID = 1L;
	private static final String NOME = "Java";
	private static final List<Desenvolvedor> DESENVOLVEDORES = Collections.singletonList(desenvolvedor);
	private static final List<Projeto> PROJETOS = Collections.singletonList(projeto);

  public static Linguagem createFakeEntity() {
      return new LinguagemBuilder()
                    .withId(ID)
                    .withNome(NOME)
                    .withDesenvolvedores(DESENVOLVEDORES)
                    .withProjetos(PROJETOS)
                    .build();
  }

}
