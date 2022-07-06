package com.gft.loja.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProdutoDTO {
	
	private Long id;
	
	private String nome;
	
	private String descricao;
	
	private String unidade;

}
