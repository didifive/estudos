package com.gft.jokenpo.service;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gft.jokenpo.model.Jogador;
import com.gft.jokenpo.model.enums.Jogada;

public class JokenpoServiceTest {
	
	private Jogador jogador;
	private Jogador jogadorDois;
	private JokenpoService jokenpoService;
	
	private void assertJogada(Jogador jogador) {
		assertThat(jogador.getJogada(), isA(Jogada.class));
	}
	
	@Before
	public void setup() {
		jogador = new Jogador("João");
		jogadorDois = new Jogador("Maria");
		jokenpoService = new JokenpoService();
	}
	
	@Test
	public void jogadorDeveFazerSuaJogadaAutomatica() throws Exception {
		jokenpoService.jogar(jogador);
		assertJogada(jogador);
	}
	
	public void jogadorDeveFazerSuaJogadaManual() throws Exception {
		jokenpoService.jogar(jogador, Jogada.PAPEL);
		assertJogada(jogador);
	}
	
	@Test
	public void deveVerificarSeJogadoresEmpataram() throws Exception {
		jokenpoService.jogar(jogador, Jogada.PAPEL);
		jokenpoService.jogar(jogadorDois, Jogada.PAPEL);
		
		int resultado = jokenpoService.verificarVencedor(jogador, jogadorDois);
		
		assertEquals(0, resultado);
		
	}
	
	@Test
	public void deveVerificarSeJogadorVence() throws Exception {
		jokenpoService.jogar(jogador, Jogada.PAPEL);
		jokenpoService.jogar(jogadorDois, Jogada.PEDRA);
		
		int resultado = jokenpoService.verificarVencedor(jogador, jogadorDois);
		
		assertEquals(1, resultado);
		
	}
	
	@Test
	public void deveVerificarSeJogadorPerde() throws Exception {
		jokenpoService.jogar(jogador, Jogada.PAPEL);
		jokenpoService.jogar(jogadorDois, Jogada.TESOURA);
		
		int resultado = jokenpoService.verificarVencedor(jogador, jogadorDois);
		
		assertEquals(2, resultado);
		
	}
	
}
