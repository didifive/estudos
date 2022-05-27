package com.gft.jokenpo.service.VerificaVencedorImpl;

import com.gft.jokenpo.model.enums.Jogada;
import com.gft.jokenpo.service.VerificaVencedor;

public class JogadaPedra implements VerificaVencedor {

	@Override
	public int verificaVencedorContra(Jogada outraJogada) {
		return (outraJogada.equals(Jogada.PEDRA)) ? 0 : 
			(outraJogada.equals(Jogada.TESOURA)) ? 1 : 2;
	}

}
