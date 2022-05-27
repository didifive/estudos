package com.gft.jokenpo.service;

import java.util.Random;

import com.gft.jokenpo.model.Jogador;
import com.gft.jokenpo.model.enums.Jogada;

public class JokenpoService {
	
	public void jogar(Jogador jogador) {
		jogador.setJogada(Jogada.values()[new Random().nextInt(Jogada.values().length)]);
	}
	
	public void jogar(Jogador jogador, Jogada jogada) {
		jogador.setJogada(jogada);
	}


	public int verificarVencedor(Jogador jogador, Jogador jogadorDois) {
		return jogador.getJogada().getValue().verificaVencedorContra(jogadorDois.getJogada());
	}

}
