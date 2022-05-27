package com.gft.jokenpo.service.VerificaVencedorImpl;

import com.gft.jokenpo.model.enums.Jogada;
import com.gft.jokenpo.service.VerificaVencedor;

public class JogadaPapel implements VerificaVencedor {

	@Override
	public int verificaVencedorContra(Jogada outraJogada) {
		return (outraJogada.equals(Jogada.PAPEL)) ? 0 : 
				(outraJogada.equals(Jogada.PEDRA)) ? 1 : 2;
	}

}
