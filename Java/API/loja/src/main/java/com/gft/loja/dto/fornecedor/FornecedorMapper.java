package com.gft.loja.dto.fornecedor;

import com.gft.loja.dto.endereco.EnderecoMapper;
import com.gft.loja.entities.Fornecedor;

public class FornecedorMapper {

	private FornecedorMapper() {
		throw new IllegalStateException("Utility class");

	}
	
	public static Fornecedor fromDTO(RegistroFornecedorDTO dto) {
		return new Fornecedor(null, dto.getCnpj(), dto.getNome(), dto.getTelefone()
															, dto.getEmail(), EnderecoMapper.fromDTO(dto.getEndereco()));
	}
	
	public static ConsultaFornecedorDTO fromEntity(Fornecedor fornecedor) {
		return new ConsultaFornecedorDTO(fornecedor.getId(), fornecedor.getCnpj(), fornecedor.getNome()
																				, fornecedor.getTelefone(), fornecedor.getEmail()
																				, EnderecoMapper.fromEntity(fornecedor.getEndereco()));
	}
	

}
