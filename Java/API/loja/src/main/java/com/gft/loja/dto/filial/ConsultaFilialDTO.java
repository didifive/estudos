package com.gft.loja.dto.filial;

import com.gft.loja.dto.endereco.EnderecoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaFilialDTO {

	private Long id;
	private String nome;
	private EnderecoDTO endereco;
}
