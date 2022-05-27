package com.gft.jokenpo.service.VerificaVencedorImpl;

import com.gft.jokenpo.model.enums.Jogada;
import com.gft.jokenpo.service.VerificaVencedor;

public class JogadaTesoura implements VerificaVencedor {

	@Override
	public int verificaVencedorContra(Jogada outraJogada) {
		return (outraJogada.equals(Jogada.TESOURA)) ? 0 : 
			(outraJogada.equals(Jogada.PAPEL)) ? 1 : 2;
	}

}
