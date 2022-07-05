package com.gft.loja.dto.endereco;

import com.gft.loja.entities.Endereco;

public class EnderecoMapper {
	
	private EnderecoMapper() {
    throw new IllegalStateException("Utility class");
  }

	
	public static Endereco fromDTO(EnderecoDTO dto) {
		return new Endereco(dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getCep());
	}
	
	public static EnderecoDTO fromEntity(Endereco endereco) {
		return new EnderecoDTO(endereco.getLogradouro(), endereco.getNumero()
														, endereco.getComplemento(), endereco.getCep());
				
	}

}
