package com.gft.jokenpo;

import java.util.Scanner;

import com.gft.jokenpo.model.Jogador;
import com.gft.jokenpo.model.enums.Jogada;
import com.gft.jokenpo.service.JokenpoService;

public class JokenpoSystem {

	public static void main(String[] args) {
		Jogador jogadorUm, jogadorDois;
		JokenpoService jokenpoService = new JokenpoService();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Seja Bem-vindo(a) ao jogo de Jokenpo!");
		System.out.println("Digite o nome do jogador(a) 1: ");
		jogadorUm = new Jogador(sc.next());
		System.out.println("Digite o nome do jogador(a) 2: ");
		jogadorDois = new Jogador(sc.next());
		fazerJogada(jogadorUm, jokenpoService, sc);
		System.out.println("=".repeat(100));
		fazerJogada(jogadorDois, jokenpoService, sc);
		System.out.println("=".repeat(100));
		System.out.println("=".repeat(100));
		System.out.println("Após as jogadas temos aqui o resultado:");
		int resultado = jokenpoService.verificarVencedor(jogadorUm, jogadorDois);
		switch (resultado) {
  		case 0:
  			System.out.println("Os jogadores EMPATARAM!");
  			break;
  		case 1:
  			System.out.println("O jogador " + jogadorUm.getNome() + " é o VENCEDOR!");
  			break;
  		case 2:
  			System.out.println("O jogador " + jogadorDois.getNome() + " é o VENCEDOR!");
  			break;
  		default:
  			System.out.println("Erro Fatal!");
  			break;
		}

		System.out.println("=".repeat(100));
		System.out.println("FIM DO PROGRAMA");
		sc.close();

	}

	private static void fazerJogada(Jogador jogador, JokenpoService jokenpoService, Scanner sc) {
		Boolean refazerJogada;
		do {
			refazerJogada = false;
  		System.out.println("Para o Jogador " + jogador.getNome() + " gostaria de fazer jogada automática? (S/N)");
  		String respostaJogadaJogadorUmString = sc.next();
  		switch (respostaJogadaJogadorUmString) {
  			case "S":
  				jokenpoService.jogar(jogador);
  				break;
  			case "N":
  				fazerJogadaManual(jogador,jokenpoService,sc);
  				break;
  			default:
  				System.out.println("Digite um número válido!");
  				refazerJogada = true;
  				break;
  		};
		}while(refazerJogada);
		System.out.println("-".repeat(100));
		System.out.println("O jogador " + jogador.getNome() + " fez a jogada: " + jogador.getJogada().toString() + "!");
	}
	
	private static void fazerJogadaManual(Jogador jogador, JokenpoService jokenpoService, Scanner sc) {
		Boolean refazerJogada;
		do {
			refazerJogada = false;
  		gerarListaDeJogadas();
  		System.out.println("Escolha o número da jogada conforme a lista acima para o jogador "+ jogador.getNome() + " ");
  		int numeroJogada = sc.nextInt();
  		if (numeroJogada > Jogada.values().length) {
  			System.out.println("Digite um número válido!");
  			refazerJogada = true;
  			break;
  		} else {
  			jokenpoService.jogar(jogador, Jogada.values()[numeroJogada-1]);
  		}
		}while(refazerJogada);
	}
	
	public static void gerarListaDeJogadas() {
		for(int i = 0; i < Jogada.values().length; i++) {
			System.out.println((i+1)+"- "+Jogada.values()[i].toString());
		}
	}

}
