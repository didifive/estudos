package com.gft.loja.dto.fornecedor;

import com.gft.loja.dto.endereco.EnderecoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaFornecedorDTO {

	private Long id;
	
	private String cnpj;
	
	private String nome;
	
	private String telefone;
	
	private String email;
	
	private EnderecoDTO endereco;
	
}
