package com.gft.loja.dto.filial;

import com.gft.loja.dto.endereco.EnderecoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroFilialDTO {
	
	private String nome;
	
	EnderecoDTO endereco;

}
