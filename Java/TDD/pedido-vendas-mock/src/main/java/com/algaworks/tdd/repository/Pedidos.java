package com.algaworks.tdd.repository;

import com.algaworks.tdd.model.Pedido;

public class Pedidos {
	
	public void guardar(Pedido pedido) {
		System.out.println("Salvando no banco de dados...");
	}
	
	public Pedido buscarPeloCodigo(Long codigo) {
		return new Pedido();
	}

}
