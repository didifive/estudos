package com.gft.jokenpo.model.enums;

import com.gft.jokenpo.service.VerificaVencedor;
import com.gft.jokenpo.service.VerificaVencedorImpl.JogadaPapel;
import com.gft.jokenpo.service.VerificaVencedorImpl.JogadaPedra;
import com.gft.jokenpo.service.VerificaVencedorImpl.JogadaTesoura;

public enum Jogada {
	
	PEDRA(new JogadaPedra()),
	PAPEL(new JogadaPapel()),
	TESOURA(new JogadaTesoura());
	
	VerificaVencedor verificaVencedor;
	
	Jogada(VerificaVencedor verificaVencedor) {
		this.verificaVencedor = verificaVencedor;
	}

	public VerificaVencedor getValue() {
		return verificaVencedor;
	}

}
