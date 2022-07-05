package com.gft.loja.dto.filial;

import com.gft.loja.dto.endereco.EnderecoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroFilialDTO {
	
	private String nome;
	
	EnderecoDTO endereco;

}
