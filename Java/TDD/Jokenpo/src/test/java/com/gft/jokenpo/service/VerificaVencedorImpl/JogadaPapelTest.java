package com.gft.jokenpo.service.VerificaVencedorImpl;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.gft.jokenpo.model.enums.Jogada;

public class JogadaPapelTest {
	
	private JogadaPapel jogada;
	
	@Before
	public void setup() {
		jogada = new JogadaPapel();
	}
	
	@Test
	public void papelDeveEmpatarComPapel() throws Exception {
		
		int resultado = jogada.verificaVencedorContra(Jogada.PAPEL);
		
		assertEquals(0, resultado);
	}
	
	@Test
	public void papelDeveVencerPedra() throws Exception {
	
		int resultado = jogada.verificaVencedorContra(Jogada.PEDRA);
		
		assertEquals(1, resultado);
	}
	
	@Test
	public void papelDevePerderDeTesoura() throws Exception {
	
		int resultado = jogada.verificaVencedorContra(Jogada.TESOURA);
		
		assertEquals(2, resultado);
	}

}
