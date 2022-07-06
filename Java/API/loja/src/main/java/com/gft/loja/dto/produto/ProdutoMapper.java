package com.gft.loja.dto.produto;

import com.gft.loja.entities.Produto;

public class ProdutoMapper {

	private ProdutoMapper() {
		throw new IllegalStateException("Utility class");

	}
	
	public static Produto fromDTO(RegistroProdutoDTO dto) {
		return new Produto(null, dto.getNome(), dto.getDescricao()
															, dto.getUnidade());
	}
	
	public static ConsultaProdutoDTO fromEntity(Produto produto) {
		return new ConsultaProdutoDTO(produto.getId(), produto.getNome()
																				, produto.getDescricao(), produto.getUnidade());
	}
	

}
