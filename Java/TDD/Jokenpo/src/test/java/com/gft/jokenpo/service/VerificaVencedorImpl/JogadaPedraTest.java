package com.gft.jokenpo.service.VerificaVencedorImpl;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.gft.jokenpo.model.enums.Jogada;

public class JogadaPedraTest {
	
	private JogadaPedra jogada;
	
	@Before
	public void setup() {
		jogada = new JogadaPedra();
	}
	
	@Test
	public void pedraDeveEmpatarComPedra() throws Exception {
		
		int resultado = jogada.verificaVencedorContra(Jogada.PEDRA);
		
		assertEquals(0, resultado);
	}
	
	@Test
	public void pedraDeveVencerTesoura() throws Exception {
		
		int resultado = jogada.verificaVencedorContra(Jogada.TESOURA);
		
		assertEquals(1, resultado);
	}
	
	@Test
	public void pedraDevePerderDePapel() throws Exception {
		
		int resultado = jogada.verificaVencedorContra(Jogada.PAPEL);
		
		assertEquals(2, resultado);
	}

}
