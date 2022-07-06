package com.gft.loja.dto.cliente;

import com.gft.loja.dto.endereco.EnderecoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroClienteDTO {
	
	private Long id;
	
	private String cpf;
	
	private String nome;
	
	private String telefone;
	
	private String email;
	
	private EnderecoDTO endereco;

}
