package com.algaworks.service;

import com.algaworks.exception.TipoPassageiroInvalidoException;
import com.algaworks.model.Passageiro;
import com.algaworks.model.TipoPassageiro;
import com.algaworks.model.Voo;

public class PrecoPassagemService {

	public double calcular(Passageiro passageiro, Voo voo) {
//		if (passageiro.getTipo().equals(TipoPassageiro.GOLD)) {
//			if (voo.getPreco() > 500) return voo.getPreco() * 0.85;
// 			return voo.getPreco() * 0.9;
//		} else if (passageiro.getTipo().equals(TipoPassageiro.SILVER)) {
//			if (voo.getPreco() > 700) return voo.getPreco() * 0.9;
// 			return voo.getPreco() * 0.94;
//		}
//		
		return passageiro.getTipo().getCalculadora().calcular(voo);
		
//		throw new TipoPassageiroInvalidoException();
	}
	
	

}
