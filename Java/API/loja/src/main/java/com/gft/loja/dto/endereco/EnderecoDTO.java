package com.gft.loja.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
	
	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String cep;
	
	

}
