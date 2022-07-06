package com.gft.loja.dto.cliente;

import com.gft.loja.dto.endereco.EnderecoMapper;
import com.gft.loja.entities.Cliente;

public class ClienteMapper {

	private ClienteMapper() {
		throw new IllegalStateException("Utility class");

	}
	
	public static Cliente fromDTO(RegistroClienteDTO dto) {
		return new Cliente(null, dto.getCpf(), dto.getNome(), dto.getTelefone()
															, dto.getEmail(), EnderecoMapper.fromDTO(dto.getEndereco()));
	}
	
	public static ConsultaClienteDTO fromEntity(Cliente cliente) {
		return new ConsultaClienteDTO(cliente.getId(), cliente.getCpf(), cliente.getNome()
																				, cliente.getTelefone(), cliente.getEmail()
																				, EnderecoMapper.fromEntity(cliente.getEndereco()));
	}
	

}
