package com.gft.projetos.utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.mockito.Mock;

import com.gft.projetos.entities.Desenvolvedor;
import com.gft.projetos.entities.Linguagem;
import com.gft.projetos.entities.Projeto;
import com.gft.projetos.entities.builders.DesenvolvedorBuilder;

public class DesenvolvedorUtils {
	
	@Mock
	private static Linguagem linguagem;
	
	@Mock
	private static Projeto projeto;
	
	private static final Long ID = 1L;
	private static final String NOME = "Bill Gates";
	private static final String QUATROLETRAS = "BIGA";
	private static final String EMAIL = "biga@gft.com";
	private static final BigDecimal SALARIOMENSAL = new BigDecimal("50000.00");
	private static final Linguagem LINGUAGEM = linguagem;
	private static final List<Projeto> PROJETOS = Collections.singletonList(projeto);
	
	private static final Long ID2 = 2L;
	private static final String NOME2 = "Bill Gates";
	private static final String QUATROLETRAS2 = "BIGA";
	private static final String EMAIL2 = "biga@gft.com";
	private static final BigDecimal SALARIOMENSAL2 = new BigDecimal("50000.00");
	private static final Linguagem LINGUAGEM2 = linguagem;
	private static final List<Projeto> PROJETOS2 = Collections.singletonList(projeto);

  public static Desenvolvedor createFakeEntity() {
      return new DesenvolvedorBuilder()
                    .withId(ID)
                    .withNome(NOME)
                    .withQuatroLetras(QUATROLETRAS)
                    .withEmail(EMAIL)
                    .withSalarioMensal(SALARIOMENSAL)
                    .withLinguagem(LINGUAGEM)
                    .withProjetos(PROJETOS)
                    .build();
  }
  
  public static Desenvolvedor createFakeEntity2() {
    return new DesenvolvedorBuilder()
                  .withId(ID2)
                  .withNome(NOME2)
                  .withQuatroLetras(QUATROLETRAS2)
                  .withEmail(EMAIL2)
                  .withSalarioMensal(SALARIOMENSAL2)
                  .withLinguagem(LINGUAGEM2)
                  .withProjetos(PROJETOS2)
                  .build();
}

}
