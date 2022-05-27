package com.algaworks.tdd.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.algaworks.tdd.email.NotificadorEmail;
import com.algaworks.tdd.exception.StatusPedidoInvalidoException;
import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.model.StatusPedido;
import com.algaworks.tdd.model.builder.PedidoBuilder;
import com.algaworks.tdd.repository.Pedidos;
import com.algaworks.tdd.sms.NotificadorSms;

public class PedidoServiceTest {
	
	private PedidoService pedidoService;
	
	private Pedido pedido;
	
	@Mock
	private Pedidos pedidos;
	@Mock
	private NotificadorEmail notificadorEmail;
	@Mock
	private NotificadorSms notificadorSms;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		pedidoService = new PedidoService(pedidos, notificadorEmail, notificadorSms);
		pedido = new PedidoBuilder()
				.comValor(100.0)
				.para("João", "joao@joao.com", "9999-0000")
				.construir();
	}
	
	@Test
	public void deveCalcularOImposto() throws Exception {
		double imposto = pedidoService.lancar(pedido);
		assertEquals(10.0, imposto, 0.0001);
	}
	
	@Test
	public void deveSalvarPedidoNoBancoDeDados() throws Exception {
    pedidoService.lancar(pedido);
    verify(pedidos).guardar(pedido);
	}
	
	@Test
	public void deveEnviarPorEmail() throws Exception {
		pedidoService.lancar(pedido);
		verify(notificadorEmail).enviar(pedido);
	}
	
	@Test
	public void deveNotificarPorSms() throws Exception {
		pedidoService.lancar(pedido);
		verify(notificadorSms).notificar(pedido);
	}
	
	@Test
	public void devePagarPedidoPendente() throws Exception {
		long codigoPedido = 135L;
		
		Pedido pedidoPendente = new Pedido();
		pedidoPendente.setStatus(StatusPedido.PENDENTE);
		when(pedidos.buscarPeloCodigo(codigoPedido)).thenReturn(pedidoPendente);
				
		Pedido pedidoPago = pedidoService.pagar(codigoPedido);
		
		assertEquals(StatusPedido.PAGO, pedidoPago.getStatus());
		
	}
	
	@Test(expected = StatusPedidoInvalidoException.class)
	public void deveNegarPagamento() throws Exception {
		long codigoPedido = 135L;
		
		Pedido pedidoPendente = new Pedido();
		pedidoPendente.setStatus(StatusPedido.PAGO);
		when(pedidos.buscarPeloCodigo(codigoPedido)).thenReturn(pedidoPendente);
				
		pedidoService.pagar(codigoPedido);
		
	}

}
