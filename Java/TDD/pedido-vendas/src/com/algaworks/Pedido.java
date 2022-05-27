package com.algaworks;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.desconto.CalculadoraFaixaDesconto;
import com.algaworks.exception.QuantidadeItensInvalidoException;

public class Pedido {
	
	private List<ItemPedido> itens = new ArrayList<>();
	
	private CalculadoraFaixaDesconto calculadoraFaixaDesconto;
	
	public Pedido(CalculadoraFaixaDesconto calculadoraFaixaDesconto) {
		this.calculadoraFaixaDesconto = calculadoraFaixaDesconto;
	}

	private void validaQuantidade(ItemPedido itemPedido) {
		if (itemPedido.getQuantidade() < 0) throw new QuantidadeItensInvalidoException();
	}

	public void adicionarItem(ItemPedido itemPedido) {
		validaQuantidade(itemPedido);
		itens.add(itemPedido);
		
	}
	
	public ResumoPedido resumo() {
		double valorTotal = itens.stream().mapToDouble(i -> i.getValorUnitario() * i.getQuantidade()).sum();
		double desconto = calculadoraFaixaDesconto.desconto(valorTotal);
	
		return new ResumoPedido(valorTotal,desconto);
	}

}
