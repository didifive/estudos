package com.algaworks.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.model.Voo;

public class PrecoPassagemGoldTest {
	
	private PrecoPassagemGold precoPassagemGold;
	
	@Before
	public void setup() {
		precoPassagemGold = new PrecoPassagemGold();
	}
	
	private void assertValorPassagemParaPassageiroGold(Voo voo, double esperado) {
		double valor = precoPassagemGold.calcular(voo);
		assertEquals(esperado, valor, 0.0001);
	}

	@Test
	public void deveCalcularValorPassagemParaPassageiroGold_ComValorAbaixoDoLimie() throws Exception {
		Voo voo = new Voo("São Paulo", "Rio de Janeiro", 100.0);
		assertValorPassagemParaPassageiroGold(voo, 90.0);
	}
	
	@Test
	public void deveCalcularValorDaPassagemParaPassageiroGold_ComValorAcimaDoLimite() throws Exception {
		Voo voo = new Voo("São Paulo", "Rio de Janeiro", 600.0);
		assertValorPassagemParaPassageiroGold(voo, 510.0);
	}

}
