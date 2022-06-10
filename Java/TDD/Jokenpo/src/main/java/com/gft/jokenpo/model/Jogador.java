package com.gft.jokenpo.model;

import com.gft.jokenpo.model.enums.Jogada;

public class Jogador {
	
	String nome;
	private Jogada jogada;
	
	public Jogador(String nome) {
		this.nome = nome;
	}
	
	public Jogador(String nome, Jogada jogada) {
		this.nome = nome;
		this.jogada = jogada;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Jogador [nome=" + nome + "]";
	}

	public Jogada getJogada() {
		return jogada;
	}

	public void setJogada(Jogada jogada) {
		this.jogada = jogada;
	}	

}
