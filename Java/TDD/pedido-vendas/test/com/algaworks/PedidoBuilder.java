package com.algaworks;

import com.algaworks.desconto.CalculadoraDescontoPrimeiraFaixa;
import com.algaworks.desconto.CalculadoraDescontoSegundaFaixa;
import com.algaworks.desconto.CalculadoraDescontoTerceiraFaixa;
import com.algaworks.desconto.CalculadoraFaixaDesconto;
import com.algaworks.desconto.SemDesconto;

public class PedidoBuilder {

		private Pedido instancia;
		
		public PedidoBuilder() {
			CalculadoraFaixaDesconto calculadoraFaixaDesconto = 
					new CalculadoraDescontoTerceiraFaixa(
						new CalculadoraDescontoSegundaFaixa(
								new CalculadoraDescontoPrimeiraFaixa(
										new SemDesconto(null))));
			instancia = new Pedido(calculadoraFaixaDesconto);
		}
		
		public PedidoBuilder comItem(double valorUnitario, int quantidade) {
			instancia.adicionarItem(new ItemPedido("Gerado", valorUnitario, quantidade));
			return this;
		}

		public Pedido construir() {
			return instancia;
		}
}
