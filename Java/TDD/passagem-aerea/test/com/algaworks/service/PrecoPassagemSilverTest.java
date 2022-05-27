package com.algaworks.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.model.Passageiro;
import com.algaworks.model.TipoPassageiro;
import com.algaworks.model.Voo;

public class PrecoPassagemSilverTest {
	
	private PrecoPassagemSilver precoPassagemSilver;
	
	@Before
	public void setup() {
		precoPassagemSilver = new PrecoPassagemSilver();
	}
	
	private void assertValorPassagemParaPassageiroSilver(Voo voo, double esperado) {
		double valor = precoPassagemSilver.calcular(voo);
		assertEquals(esperado, valor, 0.0001);
	}

	@Test
	public void deveCalcularValorPassagemParaPassageiroSilver_ComValorAbaixoDoLimie() throws Exception {
		Voo voo = new Voo("São Paulo", "Rio de Janeiro", 100.0);
		assertValorPassagemParaPassageiroSilver(voo, 94.0);
	}
	
	@Test
	public void deveCalcularValorDaPassagemParaPassageiroSilver_ComValorAcimaDoLimite() throws Exception {
		Voo voo = new Voo("São Paulo", "Rio de Janeiro", 800.0);
		assertValorPassagemParaPassageiroSilver(voo, 720.0);
	}

}
