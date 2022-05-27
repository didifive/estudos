package com.algaworks.tdd.service;

import com.algaworks.tdd.email.NotificadorEmail;
import com.algaworks.tdd.exception.StatusPedidoInvalidoException;
import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.model.StatusPedido;
import com.algaworks.tdd.repository.Pedidos;
import com.algaworks.tdd.sms.NotificadorSms;

public class PedidoService {
	
	private Pedidos pedidos;
	private NotificadorEmail notificadorEmail;
	private NotificadorSms notificadorSms;
	
	public PedidoService(Pedidos pedidos, NotificadorEmail notificadorEmail, NotificadorSms notificadorSms) {
		this.pedidos = pedidos;
		this.notificadorEmail = notificadorEmail;
		this.notificadorSms = notificadorSms;
	}

	public double lancar(Pedido pedido) {
		double imposto = pedido.getValor()*0.1;
		
		pedidos.guardar(pedido);
		notificadorEmail.enviar(pedido);
		notificadorSms.notificar(pedido);
		
		return imposto;
	}

	public Pedido pagar(long codigoPedido) {
		Pedido pedido = pedidos.buscarPeloCodigo(codigoPedido);
		
		if (!pedido.getStatus().equals(StatusPedido.PENDENTE))
			throw new StatusPedidoInvalidoException();
		
		pedido.setStatus(StatusPedido.PAGO);
		return pedido;
	}

}
