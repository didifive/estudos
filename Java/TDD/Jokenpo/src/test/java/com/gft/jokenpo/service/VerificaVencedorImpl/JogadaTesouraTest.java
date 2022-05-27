package com.gft.jokenpo.service.VerificaVencedorImpl;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.gft.jokenpo.model.enums.Jogada;

public class JogadaTesouraTest {
	
	private JogadaTesoura jogada;
	
	@Before
	public void setup() {
		jogada = new JogadaTesoura();
	}
	
	@Test
	public void tesouraDeveEmpatarComTesoura() throws Exception {
		
		int resultado = jogada.verificaVencedorContra(Jogada.TESOURA);
		
		assertEquals(0, resultado);
	}
	
	@Test
	public void tesouraDeveVencerPapel() throws Exception {
	
		int resultado = jogada.verificaVencedorContra(Jogada.PAPEL);
		
		assertEquals(1, resultado);
	}
	
	@Test
	public void tesouraDevePerderDePedra() throws Exception {
		
		int resultado = jogada.verificaVencedorContra(Jogada.PEDRA);
		
		assertEquals(2, resultado);
	}

}
